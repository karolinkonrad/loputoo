package main.ylesanne;

import main.Hindaja;
import main.Hinnang;
import main.Paisktabel;
import main.samm.LõpetusSamm;
import main.samm.Samm;
import main.samm.SisestusSamm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LisamiseYlesanne extends Ylesanne {
    private ArrayList<Integer> sisend;

    public LisamiseYlesanne(String faili_tee, Hindaja hindaja) throws IOException {
        super(hindaja);
        loeSisend(faili_tee);
        Paisktabel paisktabel = new Paisktabel(1, sisend.size());

        super.määra(this, paisktabel, sisend);
        setÕigeLäbimäng(leiaÕigeLäbimäng());
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Lisa lahtise adresseerimisega paisktabelisse samas järjekorras järgmised elemendid: " + super.abiMassiiv.toString();
    }

    @Override
    public void loeSisend(String faili_tee) throws IOException {
        File file = new File( faili_tee);
        sisend = new ArrayList<>();

        if (file.isFile() && file.getName().endsWith(".txt")) {
            List<String> read = Files.readAllLines(Path.of(file.getPath()));
            Random rand = new Random();
            String rida = read.get(rand.nextInt(read.size()));

            sisend = new ArrayList<>();

            for (String s : rida.split(" ")) {
                sisend.add(Integer.parseInt(s.replaceAll("[\\[*\\]]","")));
            }
        }
    }

    @Override
    public ArrayList<Hinnang> leiaÕigeLäbimäng() {
        // õige läbimängu sammude leidmine
        Paisktabel p = new Paisktabel(1, sisend.size());

        ArrayList<Hinnang> õigeLäbimäng = new ArrayList<>();

        for (Integer arv : sisend) {
            int räsi = paiskfunktsioon(arv);
            int koht = p.leiaVabaKoht(räsi);

            if (räsi != koht)
                õigeLäbimäng.add(new Hinnang(new SisestusSamm(0, koht, 0), hindaja.RASKEOP, null, true));
            else õigeLäbimäng.add(new Hinnang(new SisestusSamm(0, koht, 0), hindaja.LISAMINE, null, true));

            p.sisesta(koht, 0, arv);
        }

        õigeLäbimäng.add(new Hinnang(new LõpetusSamm(), hindaja.LÕPP, null, true));
        return õigeLäbimäng;
    }

    @Override
    public Hinnang hindaSammu(Samm samm) {
        // tagastab vea tüübi

        Samm õigeSamm = new LõpetusSamm();

        if (abiMassiiv.size() > 0) { // veel on lisamata kirjeid
            int arv = (int) abiMassiiv.get(0);

            int räsi = paiskfunktsioon(arv);
            int vabaRäsi = paisktabel.leiaVabaKoht(räsi);
            õigeSamm = new SisestusSamm(0, vabaRäsi, 0);

            if (vabaRäsi == räsi) { // nihutamisi ei tehta
                return new Hinnang(õigeSamm, hindaja.LISAMINE, samm, õigeSamm.equals(samm));
            }
            else
                return new Hinnang(õigeSamm, hindaja.RASKEOP, samm, õigeSamm.equals(samm));

        }
        // algoritm lõpetab
        return new Hinnang(õigeSamm, hindaja.LÕPP, samm, õigeSamm.equals(samm));
    }

}
