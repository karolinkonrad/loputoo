package main.ylesanne;

import main.Hinnang;
import main.Läbimäng;
import main.Paisktabel;
import main.samm.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static main.Hindaja.Olek.*;

public class KimbuYlesanne extends Ylesanne<Float>{
    private ArrayList<Float> sisend;
    private int kompesamm;

    private int elementideArv;
    private float minElem;
    private float maxElem;
    private boolean sisestamine;

    private int tudengiElementideArv;
    private float tudengiMinElem;
    private float tudengiMaxElem;


    public KimbuYlesanne(String faili_tee) throws IOException {
        loeSisend(faili_tee);
    }

    /**
     * Arv räsi arvutamine ühtlase paiskamisega.
     * @param arv Antud arv.
     * @return Arvu räsi
     */
    public int paiskfunktsioon(float arv) {
        return (int) Math.floor((arv-tudengiMinElem) / (tudengiMaxElem-tudengiMinElem) * tudengiElementideArv);
    }


    /**
     * Failist ülesande andmete lugemine.
     * Rakendatakse konstruktoris. Salvestab sisendi, kompesammu, maxElemendi, minElemendi ja elementideArvu.
     * @param failiTee Antud faili tee
     * @throws IOException
     */
    @Override
    public void loeSisend(String failiTee) throws IOException {
        File file = new File(failiTee);
        sisend = new ArrayList<>();

        if (file.isFile() && file.getName().endsWith(".txt")) {
            List<String> read = Files.readAllLines(Path.of(file.getPath()));
            Random rand = new Random();
            String rida = read.get(rand.nextInt(read.size()));

            sisend = new ArrayList<>();

            minElem = Float.MAX_VALUE;
            maxElem = Float.MIN_VALUE;

            for (String s : rida.split(" ")) {
                float arv = Float.parseFloat(s.replaceAll("[\\[\\]]",""));
                if (arv < minElem) minElem = arv;
                if (maxElem < arv) maxElem = arv;
                sisend.add(arv);
            }
            maxElem = (float) Math.ceil(maxElem);

            setPaisktabeliParameetrid(minElem, maxElem, sisend.size());
        }
        kompesamm = 0;
    }

    /**
     * Algse paisktabeli andmine.
     * @return Uus tühi paisktabel
     */
    @Override
    public Paisktabel<Float> getPaisktabel() {
        return new Paisktabel<>(kompesamm);
    }

    /**
     * Algse abijärjendi andmine.
     * @return Koopia sisendist.
     */
    @Override
    public ArrayList<Float> getAbijärjend() {
        return new ArrayList<>(sisend);
    }


    @Override
    public void setPaisktabeliParameetrid(float minElem, float maxElem, int elementideArv) {
        this.tudengiMinElem = minElem;
        this.tudengiMaxElem = maxElem;
        this.tudengiElementideArv = elementideArv;
    }


    @Override
    public ArrayList<Hinnang> leiaÕigeLäbimäng() {

        ArrayList<Hinnang> õigeLäbimäng = new ArrayList<>();

        elementideArv = sisend.size();

        Paisktabel<Float> p = new Paisktabel<>(0, elementideArv);
        õigeLäbimäng.add(new Hinnang(new PaisktabeliLoomiseSamm(minElem, maxElem, elementideArv), TABELI_LOOMINE, null, true));

        for (Float arv : sisend) {
            int räsi = paiskfunktsioon(arv);

            int i;
            for (i = 0; i < p.get(räsi).size(); i++) {
                if (arv <= (float) p.get(räsi, i)) break;
            }

            if (p.get(räsi).size() > 0) õigeLäbimäng.add(new Hinnang(new SisestamiseSamm<Float>(0, räsi, i), RASKE_LISAMINE, null, true));
            else õigeLäbimäng.add(new Hinnang(new SisestamiseSamm<Float>(0, räsi, i), LISAMINE, null, true));

            p.sisesta(räsi, i, arv);
        }

        sisend.clear();
        for (int i = 0; i < p.size(); i++) {
            while (p.get(i).size() > 0){
                sisend.add(p.get(i, 0));
                p.eemalda(i, 0);
                õigeLäbimäng.add(new Hinnang(new EemaldamiseSamm<Float>(sisend.size()-1, i, 0), EEMALDAMINE, null, true));
            }
        }

        õigeLäbimäng.add(new Hinnang(new LõpetamiseSamm(), LÕPP, null, true));

        sisestamine = true;
        return õigeLäbimäng;
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Järjestada ahel kimbumeetodil: " + sisend.toString();
    }

    /**
     * Oleku muutmine, kas on vaja elemente sisestada paisktabelisse või sealt eemaldada.
     * @param läbimäng Jooksev läbimäng.
     * @param hinnang Viimase sammu hinnang.
     */
    @Override
    public void astu(Läbimäng<Float> läbimäng, Hinnang hinnang) {
        if (läbimäng.getAbijärjend().size() == 0 && sisestamine)
            sisestamine = false;
    }

    /**
     * Oleku muutuse tagasivõtmine, kas on vaja elemente sisestada paisktabelisse või sealt eemaldada.
     * @param läbimäng Jooksev läbimäng.
     * @param hinnang Viimase sammu hinnang.
     */
    @Override
    public void tagasi(Läbimäng<Float> läbimäng, Hinnang hinnang) {
        if (läbimäng.getAbijärjend().size() == 1 && !sisestamine
                && (hinnang.olek == LISAMINE || hinnang.olek == RASKE_LISAMINE))
            sisestamine = true;
    }

    @Override
    public Hinnang hindaSammu(Samm samm, ArrayList<Float> abijärjend, Paisktabel<Float> paisktabel) {

        Samm õigeSamm = new LõpetamiseSamm();

        if (paisktabel.size() == 0) {
            õigeSamm = new PaisktabeliLoomiseSamm(minElem, maxElem, elementideArv);
            return new Hinnang(õigeSamm, TABELI_LOOMINE, samm, õigeSamm.equals(samm));
        }

        if (abijärjend.size() > 0 && sisestamine) { // veel on lisamata kirjeid
            float arv = abijärjend.get(0);

            int räsi = paiskfunktsioon(arv);
            int i;
            for (i = 0; i < paisktabel.get(räsi).size(); i++) {
                if (arv <= paisktabel.get(räsi, i)) break;
            }
            õigeSamm = new SisestamiseSamm<Float>(0, räsi, i);

            if (paisktabel.get(räsi).size() == 0) { // Lisatakse tühja kimpu
                return new Hinnang(õigeSamm, LISAMINE, samm, õigeSamm.equals(samm));
            }
            else
                return new Hinnang(õigeSamm, RASKE_LISAMINE, samm, õigeSamm.equals(samm));

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
