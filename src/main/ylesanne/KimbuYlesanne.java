package main.ylesanne;

import main.Hinnang;
import main.Paisktabel;
import main.samm.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static main.Hindaja.*;

public class KimbuYlesanne extends Ylesanne{
    private ArrayList<Float> sisend;
    private int kompesamm;

    private int elementideArv;
    private float minElem;
    private float maxElem;
    private int järg;

    private int tudengiElementideArv;
    private float tudengiMinElem;
    private float tudengiMaxElem;


    public KimbuYlesanne(String faili_tee) throws IOException {
        loeSisend(faili_tee);
    }

    public int paiskfunktsioon(float arv) {
        return (int) Math.floor((arv-tudengiMinElem) / (tudengiMaxElem-tudengiMinElem) * tudengiElementideArv);
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

            minElem = Float.MAX_VALUE;
            maxElem = Float.MIN_VALUE;

            for (String s : rida.split(" ")) {
                float arv = Float.valueOf(s.replaceAll("[\\[\\]]",""));
                if (arv < minElem) minElem = arv;
                if (maxElem < arv) maxElem = arv;
                sisend.add(arv);
            }
            maxElem = (float) Math.ceil(maxElem);

            paisktabeliParameetrid(minElem, maxElem, elementideArv);
        }
        kompesamm = 0;
    }

    @Override
    public Paisktabel getPaisktabel() {
        return new Paisktabel(kompesamm);
    }

    @Override
    public ArrayList getAbijärjend() {
        return (ArrayList) sisend.clone();
    }

    @Override
    public void paisktabeliParameetrid(float minElem, float maxElem, int elementideArv) {
        this.tudengiMinElem = minElem;
        this.tudengiMaxElem = maxElem;
        this.tudengiElementideArv = elementideArv;
    }

    @Override
    public ArrayList<Hinnang> leiaÕigeLäbimäng() {
        // õige läbimängu sammude leidmine

        ArrayList<Hinnang> õigeLäbimäng = new ArrayList<>();

        elementideArv = sisend.size();

        Paisktabel p = new Paisktabel(0, elementideArv);
        õigeLäbimäng.add(new Hinnang(new PaisktabeliLoomiseSamm(minElem, maxElem, elementideArv), TABELIOP, null, true));

        for (Float arv : sisend) {
            int räsi = paiskfunktsioon(arv);

            int i;
            for (i = 0; i < p.get(räsi).size(); i++) {
                if (arv <= (float) p.get(räsi, i)) break;
            }

            if (p.get(räsi).size() > 0) õigeLäbimäng.add(new Hinnang(new SisestamiseSamm(0, räsi, i), RASKEOP, null, true));
            else õigeLäbimäng.add(new Hinnang(new SisestamiseSamm(0, räsi, i), LISAMINE, null, true));

            p.sisesta(räsi, i, arv);
        }

        sisend.clear();
        for (int i = 0; i < p.size(); i++) {
            while (p.get(i).size() > 0){
                sisend.add((Float) p.get(i, 0));
                p.eemalda(i, 0);
                õigeLäbimäng.add(new Hinnang(new EemaldamiseSamm(sisend.size()-1, i, 0), EEMALDAMINE, null, true));
            }
        }

        õigeLäbimäng.add(new Hinnang(new LõpetamiseSamm(), LÕPP, null, true));

        järg = 0;
        return õigeLäbimäng;
    }

    @Override
    public void astu(Hinnang hinnang) {
        if (hinnang.olek == EEMALDAMINE && hinnang.õige) järg++;
    }

    @Override
    public void tagasi(Hinnang hinnang) {
        if (hinnang.olek == EEMALDAMINE && hinnang.õige) järg--;
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Järjestada ahel kimbumeetodil" + sisend.toString();
    }

    @Override
    public Hinnang hindaSammu(Samm samm, ArrayList abijärjend, Paisktabel paisktabel) {
        // tagastab vea tüübi

        Samm õigeSamm = new LõpetamiseSamm();

        if (paisktabel.size() == 0) {
            õigeSamm = new PaisktabeliLoomiseSamm(minElem, maxElem, elementideArv);
            return new Hinnang(õigeSamm, TABELIOP, samm, õigeSamm.equals(samm));
        }

        if (abijärjend.size() > 0 && järg == 0) { // veel on lisamata kirjeid
            float arv = (float) abijärjend.get(0);

            int räsi = paiskfunktsioon(arv);
            int i;
            for (i = 0; i < paisktabel.get(räsi).size(); i++) {
                if (arv <= (float) paisktabel.get(räsi, i)) break;
            }
            õigeSamm = new SisestamiseSamm(0, räsi, i);

            if (paisktabel.get(räsi).size() == 0) { // Lisatakse tühja kimpu
                return new Hinnang(õigeSamm, LISAMINE, samm, õigeSamm.equals(samm));
            }
            else
                return new Hinnang(õigeSamm, RASKEOP, samm, õigeSamm.equals(samm));

        }
        else {
            for (int i = 0; i < paisktabel.size(); i++) {
                if (paisktabel.get(i).size() > 0) {
                    õigeSamm = new EemaldamiseSamm<Float>(abijärjend.size(), i, 0);
                    return new Hinnang(õigeSamm, EEMALDAMINE, samm, õigeSamm.equals(samm));
                }
            }
        }

        // algoritm lõpetab
        return new Hinnang(õigeSamm, LÕPP, samm, õigeSamm.equals(samm));
    }

}
