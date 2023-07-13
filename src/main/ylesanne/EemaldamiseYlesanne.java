package main.ylesanne;

import main.Hindaja;
import main.Hinnang;
import main.Paisktabel;
import main.samm.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class EemaldamiseYlesanne extends Ylesanne{
    private ArrayList<Integer> sisend;

    private int eemaldatav;
    private int eemaldatavaRäsi;
    private HashMap<Integer, Integer> kompejadaAlgsedIndeksid;
    private int järg;


    public EemaldamiseYlesanne(String faili_tee, Hindaja hindaja) throws IOException {
        super(hindaja);

        loeSisend(faili_tee);
        Paisktabel paisktabel = new Paisktabel(1);
        paisktabel.looPaisktabel(sisend.size());
        for (Integer arv : sisend) {
            paisktabel.sisesta( paisktabel.leiaVabaKoht(paiskfunktsioon(arv)), 0, arv);
        }

        super.määra(this, paisktabel, new ArrayList<>());
        setÕigeLäbimäng(leiaÕigeLäbimäng());
    }

    @Override
    protected void astuJärg(Hinnang hinnang) {
        if ((hinnang.liik == hindaja.EEMALDAMINE || hinnang.liik == hindaja.KUSTUTAMINE) && hinnang.õige)
            järg++;
    }

    @Override
    protected void tagasiJärg(Hinnang hinnang) {
        if ((hinnang.liik == hindaja.EEMALDAMINE || hinnang.liik == hindaja.KUSTUTAMINE) && hinnang.õige)
            järg--;
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Eemalda lahtise adresseerimiesega paisktabelist " + eemaldatav;
    }

    @Override
    public void loeSisend(String faili_tee) throws IOException {
        File file = new File(faili_tee);
        sisend = new ArrayList<>();
        if (file.isFile() && file.getName().endsWith(".txt")) {
            List<String> read = Files.readAllLines(Path.of(file.getPath()));
            Random rand = new Random();
            String rida = read.get(rand.nextInt(read.size()));

            sisend = new ArrayList<>();

            for (String s : rida.split(" ")) {
                if (s.contains("*"))
                    eemaldatav = Integer.parseInt(s.replaceAll("[\\[*\\]]",""));
                sisend.add(Integer.parseInt(s.replaceAll("[\\[*\\]]","")));
            }

        }
    }

    @Override
    public ArrayList<Hinnang> leiaÕigeLäbimäng() {
        // õige läbimängu sammude leidmine

        Paisktabel p = new Paisktabel(1);
        p.looPaisktabel(sisend.size());

        for (Integer arv : sisend) {
            p.sisesta( p.leiaVabaKoht(paiskfunktsioon(arv)),0, arv);
        }

        kompejadaAlgsedIndeksid = new HashMap<>();
        ArrayList<Hinnang> õigeLäbimäng = new ArrayList<>();

        eemaldatavaRäsi = p.leiaAsukoht(eemaldatav, paiskfunktsioon(eemaldatav));
        p.eemalda(eemaldatavaRäsi, 0);

        õigeLäbimäng.add(new Hinnang(new EemaldusSamm(0, eemaldatavaRäsi, 0), hindaja.KUSTUTAMINE, null, true));

        int i = eemaldatavaRäsi;

        while (true){ // kompejada läbimine
            i++;
            if (i >= p.size()) i = 0;
            if (i == eemaldatavaRäsi) break;
            if (p.get(i, 0) == null) break;

            int arv = (int) p.get(i, 0);
            kompejadaAlgsedIndeksid.put(arv, i);
            p.eemalda(i, 0);
            õigeLäbimäng.add(new Hinnang(new EemaldusSamm(0, i, 0), hindaja.EEMALDAMINE, null, true));

            int uusKoht = p.leiaVabaKoht(paiskfunktsioon(arv));
            if (uusKoht != i)
                õigeLäbimäng.add(new Hinnang(new SisestusSamm(0, uusKoht, 0), hindaja.RASKEOP, null, true));
            else õigeLäbimäng.add(new Hinnang(new SisestusSamm(0, uusKoht, 0), hindaja.LISAMINE, null, true));
            p.sisesta(uusKoht, 0, arv);

        }
        õigeLäbimäng.add(new Hinnang(new LõpetusSamm(), hindaja.LÕPP, null, true));

        järg = 0;

        return õigeLäbimäng;
    }

    @Override
    public Hinnang hindaSammu(Samm samm) {
        Samm õigeSamm = new LõpetusSamm();


        if (abiMassiiv.size() == 0) { // kas eemaldatav on eemaldatud?
            int eemaldatavaVõti = paisktabel.leiaAsukoht(eemaldatav, paiskfunktsioon(eemaldatav));
            õigeSamm = new EemaldusSamm(0, eemaldatavaVõti, 0);

            return new Hinnang(õigeSamm, hindaja.KUSTUTAMINE, samm, õigeSamm.equals(samm));
        }

        // kompejada läbimine

        if (abiMassiiv.size() > 1) { // kas on kirjeid tagasi panna?
            int arv = (int) abiMassiiv.get(0);
            int räsi = paiskfunktsioon(arv);
            int vabaRäsi = paisktabel.leiaVabaKoht(räsi);

            õigeSamm = new SisestusSamm(0, vabaRäsi, 0);

            if (vabaRäsi != kompejadaAlgsedIndeksid.get(arv)) { // kas uus vabaRäsi on erinev vanast võtmest?
                return new Hinnang(õigeSamm, hindaja.RASKEOP, samm, õigeSamm.equals(samm));
            }
            else
                return new Hinnang(õigeSamm, hindaja.LISAMINE, samm, õigeSamm.equals(samm));
        }

        // kas on kirjeid vales kohas?

        if (järg >= paisktabel.size()) järg = 0;
        if (järg == eemaldatavaRäsi || paisktabel.get(järg).size() == 0)
            // algoritm lõpetab 
            return new Hinnang(õigeSamm, hindaja.LÕPP, samm, õigeSamm.equals(samm));

        õigeSamm = new EemaldusSamm(0, järg, 0);
        return new Hinnang(õigeSamm, hindaja.EEMALDAMINE, samm, õigeSamm.equals(samm));
        
    }

}
