package main.ylesanne;

import main.Hindaja;
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
    protected void astuJärg(int hinnang) {
        if (hinnang == EEMALDAMINE || hinnang == KUSTUTAMINE)
            järg++;
    }

    @Override
    protected void tagasiJärg(int hinnang) {
        if (hinnang == EEMALDAMINE || hinnang == KUSTUTAMINE)
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
    public ArrayList<Integer> leiaÕigeLäbimäng() {
        // õige läbimängu sammude leidmine

        Paisktabel p = new Paisktabel(1);
        p.looPaisktabel(sisend.size());

        for (Integer arv : sisend) {
            p.sisesta( p.leiaVabaKoht(paiskfunktsioon(arv)),0, arv);
        }

        kompejadaAlgsedIndeksid = new HashMap<>();
        ArrayList<Integer> õigeLäbimäng = new ArrayList<>();

        eemaldatavaRäsi = p.leiaAsukoht(eemaldatav, paiskfunktsioon(eemaldatav));
        p.eemalda(eemaldatavaRäsi, 0);
        õigeLäbimäng.add(KUSTUTAMINE);

        int i = eemaldatavaRäsi;

        while (true){ // kompejada läbimine
            i++;
            if (i >= p.size()) i = 0;
            if (i == eemaldatavaRäsi) break;
            if (p.get(i, 0) == null) break;

            int arv = (int) p.get(i, 0);
            kompejadaAlgsedIndeksid.put(arv, i);
            p.eemalda(i, 0);
            õigeLäbimäng.add(EEMALDAMINE);

            int uusKoht = p.leiaVabaKoht(paiskfunktsioon(arv));
            if (uusKoht != i)
                õigeLäbimäng.add(RASKEOP);
            else õigeLäbimäng.add(LISAMINE);
            p.sisesta(uusKoht, 0, arv);

        }
        õigeLäbimäng.add(LÕPP);

        järg = 0;

        return õigeLäbimäng;
    }

    @Override
    public int hindaSammu(Samm samm) {
        // tagastab vea tüübi

        // if eemaldatav on eemaldatud
        // if kirjed on üles võetud
        // if kirjed on tagasi pandud

        Samm õigeSamm = new LopetusSamm();


        if (abiMassiiv.size() == 0) { // kas eemaldatav on eemaldatud?
            int eemaldatavaVõti = paisktabel.leiaAsukoht(eemaldatav, paiskfunktsioon(eemaldatav));
            õigeSamm = new EemaldusSamm(0, eemaldatavaVõti, 0);

            return õigeSamm.equals(samm) ? KUSTUTAMINE: -KUSTUTAMINE;
        }

        // kompejada läbimine

        if (abiMassiiv.size() > 1) { // kas on kirjeid tagasi panna?
            int arv = (int) abiMassiiv.get(0);
            int räsi = paiskfunktsioon(arv);
            int vabaRäsi = paisktabel.leiaVabaKoht(räsi);

            õigeSamm = new SisestusSamm(0, vabaRäsi, 0);

            if (vabaRäsi != kompejadaAlgsedIndeksid.get(arv)) { // kas uus vabaRäsi on erinev vanast võtmest?
                return õigeSamm.equals(samm) ? RASKEOP: -RASKEOP;
            }
            else
                return õigeSamm.equals(samm) ? LISAMINE: -LISAMINE;
        }

        // kas on kirjeid vales kohas?

        if (järg >= paisktabel.size()) järg = 0;
        if (järg == eemaldatavaRäsi || paisktabel.get(järg).size() == 0)
            // algoritm lõpetab 
            return õigeSamm.equals(samm) ? LÕPP : -LÕPP;
        
        õigeSamm = new EemaldusSamm(0, järg, 0);
        return õigeSamm.equals(samm) ? EEMALDAMINE : -EEMALDAMINE;
        
    }

}
