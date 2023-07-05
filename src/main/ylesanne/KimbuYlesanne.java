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

public class KimbuYlesanne extends Ylesanne {
    private int elementideArv;
    private float minElem;
    private float maxElem;
    private int järg;

    private int tudengiElementideArv;
    private float tudengiMinElem;
    private float tudengiMaxElem;

    public KimbuYlesanne(String faili_tee) throws IOException {
        super();
        ArrayList<Float> sisend = loeSisend(faili_tee);

        super.määra(this, new Paisktabel(0), sisend);

        // õige läbimängu sammude leidmine

        Paisktabel p = new Paisktabel(0);
        ArrayList<Integer> õigedTüübid = new ArrayList<>();

        elementideArv = sisend.size();

        p.looPaisktabel(elementideArv);
        õigedTüübid.add(TABELIOP);

        for (Float arv : sisend) {
            int voti = paiskfunktsioon(arv);

            int i;
            for (i = 0; i < p.get(voti).size(); i++) {
                if (arv <= (float) p.get(voti, i)) break;
            }

            p.sisesta(voti, i, arv);

            if (i > 0) õigedTüübid.add(RASKEOP);
            else õigedTüübid.add(LISAMINE);
        }

        for (Float _arv : sisend) {
            õigedTüübid.add(EEMALDAMINE);
        }

        õigedTüübid.add(LÕPP);

        järg = 0;
        setÕigedTüübid(õigedTüübid);
    }

    private ArrayList<Float> loeSisend(String faili_tee) throws IOException {
        File file = new File(faili_tee);
        ArrayList<Float> sisend = new ArrayList<>();

        if (file.isFile() && file.getName().endsWith(".txt")) {
            List<String> read = Files.readAllLines(Path.of(file.getPath()));
            Random rand = new Random();
            String rida = read.get(rand.nextInt(read.size()));

            sisend = new ArrayList<>();

            minElem = Float.MAX_VALUE;
            maxElem = Float.MIN_VALUE;

            for (String s : rida.split(" ")) {
                float arv = Float.valueOf(s.replaceAll("[\\[\\]]",""));
                if (arv < minElem) minElem = arv;
                if (maxElem < arv) maxElem = arv;
                sisend.add(arv);
            }
        }
        return sisend;
    }

    public int paiskfunktsioon(float arv) {
        return (int) Math.floor((arv-minElem) / (Math.ceil(maxElem)-minElem) * elementideArv);
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Järjestada kimbumeetodil järgmised arvud: " + super.getAbiMassiiv().toString();
    }

    @Override
    public void paisktabeliParameetrid(float minElem, float maxElem, int elementideArv) {
        this.tudengiMinElem = minElem;
        this.tudengiMaxElem = maxElem;
        this.tudengiElementideArv = elementideArv;
    }

    @Override
    protected void astuJärg(int hinnang) {
        if (hinnang == EEMALDAMINE) järg++;
    }

    @Override
    protected void tagasiJärg(int hinnang) {
        if (hinnang == EEMALDAMINE) järg--;
    }

    @Override
    public int hindaSammu(Samm samm) {
        // tagastab vea tüübi

        Samm õigeSamm = new LopetusSamm();

        if (paisktabel.size() == 0) {
            õigeSamm = new PaisktabeliLoomisSamm(minElem, maxElem, elementideArv);
            return õigeSamm.equals(samm) ? TABELIOP: -TABELIOP;
        }

        if (abiMassiiv.size() > 0 && järg == 0) { // veel on lisamata kirjeid
            float arv = (float) abiMassiiv.get(0);

            int voti = paiskfunktsioon(arv);
            int i;
            for (i = 0; i < paisktabel.get(voti).size(); i++) {
                if (arv <= (float) paisktabel.get(voti, i)) break;
            }
            õigeSamm = new SisestusSamm(0, voti, i);

            if (paisktabel.get(voti).size() == 0) { // Lisatakse tühja kimpu
                return õigeSamm.equals(samm) ? LISAMINE: -LISAMINE;
            }
            else
                return õigeSamm.equals(samm) ? RASKEOP: -RASKEOP;

        }
        else {
            for (int i = 0; i < paisktabel.size(); i++) {
                if (paisktabel.get(i).size() > 0) {
                    õigeSamm = new EemaldusSamm<Float>(abiMassiiv.size(), i, 0);
                    return õigeSamm.equals(samm) ? EEMALDAMINE : -EEMALDAMINE;
                }
            }
        }

        // algoritm lõpetab
        return õigeSamm.equals(samm) ? LÕPP : -LÕPP;
    }

}
