package main.ylesanne;

import main.Paisktabel;
import main.samm.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PositsiooniYlesanne extends Ylesanne {
    int maxElem;
    int järk;
    private boolean sisestamine;

    public PositsiooniYlesanne(String faili_tee) throws IOException {
        super();
        ArrayList<Integer> sisend = loeSisend(faili_tee);

        super.määra(this, new Paisktabel(0), sisend);


        // õige läbimängu sammude leidmine

        Paisktabel p = new Paisktabel(0);
        ArrayList<Integer> õigedTüübid = new ArrayList<>();

        p.looPaisktabel(10);
        õigedTüübid.add(TABELIOP);

        järk = 0;
        while ( (maxElem / Math.pow(10, järk)) < 10 ) {

            for (int arv : sisend) {

                int voti = paiskfunktsioon(arv);
                int i = p.get(voti).size();

                p.sisesta(voti, i, arv);

                if (i > 0) õigedTüübid.add(RASKEOP);
                else õigedTüübid.add(LISAMINE);
            }

            sisend.clear();
            for (int i = 0; i < p.size(); i++) {
                while (p.get(i).size() > 0){
                    sisend.add((Integer) p.get(i, 0));
                    p.eemalda(i, 0);
                    õigedTüübid.add(EEMALDAMINE);
                }
            }

            järk++;
        }

        õigedTüübid.add(LÕPP);

        järk = 0;
        sisestamine = true;
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

            maxElem = Integer.MIN_VALUE;
            for (String s : rida.split(" ")) {
                int elem = Integer.parseInt(s.replaceAll("[\\[\\]]",""));
                if (maxElem < elem) maxElem = elem;
                sisend.add(elem);
            }
        }
        return sisend;
    }

    public int paiskfunktsioon(int arv) {
        return (int) (arv / Math.pow(10, järk)) % 10;
    }

    @Override
    protected void astuJärg(int hinnang) {
        if (hinnang == EEMALDAMINE && sisestamine) {
            sisestamine = false;
        }
        if (hinnang == LISAMINE && !sisestamine) {
            sisestamine = true;
            järk++;
        }
    }

    @Override
    protected void tagasiJärg(int hinnang) {
        if (hinnang == EEMALDAMINE && sisestamine) {
            sisestamine = false;
        }
        if (hinnang == LISAMINE && !sisestamine) {
            sisestamine = true;
            järk--;
        }
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Järjestada positsioonimeetodil järgmised arvud: " + abiMassiiv;
    }

    @Override
    public int hindaSammu(Samm samm) {
        Samm õigeSamm = new LopetusSamm();


        if (paisktabel.size() == 0) {
            õigeSamm = new PaisktabeliLoomisSamm(abiMassiiv.size());
            return õigeSamm.equals(samm) ? TABELIOP: -TABELIOP;
        }

        if ((maxElem / Math.pow(10, järk)) < 10) {


            if (abiMassiiv.size() > 0 && sisestamine) {
                int arv = (int) abiMassiiv.get(0);

                int voti = paiskfunktsioon(arv);
                int koht = paisktabel.get(voti).size();

                õigeSamm = new SisestusSamm(0, voti, koht);

                if (koht == 0) { // Lisatakse tühja kimpu
                    return õigeSamm.equals(samm) ? LISAMINE : -LISAMINE;
                } else
                    return õigeSamm.equals(samm) ? RASKEOP : -RASKEOP;
            }

            else {
                for (int i = 0; i < paisktabel.size(); i++) {
                    if (paisktabel.get(i).size() > 0) {
                        õigeSamm = new EemaldusSamm<Float>(abiMassiiv.size(), i, 0);
                        return õigeSamm.equals(samm) ? EEMALDAMINE : -EEMALDAMINE;
                    }
                }
            }


        }
        // algoritm lõpetab
        return õigeSamm.equals(samm) ? LÕPP : -LÕPP;


    }

}
