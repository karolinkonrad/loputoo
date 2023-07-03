package main.ylesanne;

import main.Paisktabel;
import main.samm.LopetusSamm;
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

    public LisamiseYlesanne(String faili_tee) throws IOException {
        super();
        ArrayList<Integer> sisend = loeSisend(faili_tee);
        Paisktabel paisktabel = new Paisktabel(1);
        paisktabel.looPaisktabel(sisend.size());

        super.määra(this, paisktabel, sisend);

        // õige läbimängu sammude leidmine
        Paisktabel p = new Paisktabel(1);
        p.looPaisktabel(sisend.size());

        ArrayList<Integer> õigedTüübid = new ArrayList<>();

        for (Integer arv : sisend) {
            int voti = räsi(arv);
            int koht = p.leiaVabaKoht(voti);

            if (voti != koht)
                õigedTüübid.add(RASKEOP);
            else õigedTüübid.add(LISAMINE);

            p.sisesta(koht, 0, arv);
        }

        setÕigedTüübid(õigedTüübid);
    }

    private ArrayList<Integer> loeSisend(String faili_tee) throws IOException {
        File file = new File( faili_tee);
        ArrayList<Integer> sisend = new ArrayList<>();

        if (file.isFile() && file.getName().endsWith(".txt")) {
            List<String> read = Files.readAllLines(Path.of(file.getPath()));
            Random rand = new Random();
            String rida = read.get(rand.nextInt(read.size()));

            sisend = new ArrayList<>();

            for (String s : rida.split(" ")) {
                sisend.add(Integer.parseInt(s.replaceAll("[\\[*\\]]","")));
            }

        }
        return sisend;
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Lisa lahtise adresseerimisega paisktabelisse samas järjekorras järgmised elemendid: " + super.abiMassiiv.toString();
    }

    @Override
    public Integer hindaSammu(Samm samm) {
        // tagastab vea tüübi

        Samm õigeSamm = new LopetusSamm();

        if (abiMassiiv.size() > 0) { // veel on lisamata kirjeid
            int arv = (int) abiMassiiv.get(0);

            int voti = räsi(arv);
            int koht = paisktabel.leiaVabaKoht(voti);
            õigeSamm = new SisestusSamm(koht, arv, 0);

            if (koht == voti) { // nihutamisi ei tehta
                return õigeSamm.equals(samm) ? LISAMINE: -LISAMINE;
            }
            else
                return õigeSamm.equals(samm) ? RASKEOP: -RASKEOP;

        }
        // algoritm lõpetab
        return õigeSamm.equals(samm) ? LÕPP : -LÕPP;
    }

}
