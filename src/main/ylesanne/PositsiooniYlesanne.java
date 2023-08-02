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

public class PositsiooniYlesanne extends Ylesanne<Integer> {
    private ArrayList<Integer> sisend;
    private int kompesamm;
    private int maxElem;

    private int järk;
    private boolean sisestamine;

    public PositsiooniYlesanne(String faili_tee) throws IOException {
        loeSisend(faili_tee);
    }

    public int paiskfunktsioon(int arv) {
        return (int) (arv / Math.pow(10, järk)) % 10;
    }

    /**
     * Failist ülesande andmete lugemine.
     * Rakendatakse konstruktoris. Salvestab sisendi, kompesammu ja maxElemendi.
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

            maxElem = Integer.MIN_VALUE;
            for (String s : rida.split(" ")) {
                int elem = Integer.parseInt(s.replaceAll("[\\[\\]]",""));
                if (maxElem < elem) maxElem = elem;
                sisend.add(elem);
            }
            kompesamm = 0;
        }
    }

    /**
     * Algse paisktabeli andmine.
     * @return Uus tühi paisktabel
     */
    @Override
    public Paisktabel<Integer> getPaisktabel() {
        return new Paisktabel<>(kompesamm);
    }

    /**
     * Algse abijärjendi andmine.
     * @return Koopia sisendist.
     */
    @Override
    public ArrayList<Integer> getAbijärjend() {
        return new ArrayList<>(sisend);
    }

    //pole vajalik
    @Override
    public void setPaisktabeliParameetrid(float minElem, float maxElem, int elementideArv) {
    }

    @Override
    public ArrayList<Hinnang> leiaÕigeLäbimäng() {

        ArrayList<Hinnang> õigeLäbimäng = new ArrayList<>();

        Paisktabel<Integer> p = new Paisktabel<>(0, 10);
        õigeLäbimäng.add(new Hinnang(new PaisktabeliLoomiseSamm(10), TABELI_LOOMINE, null, true));

        järk = 0;
        while ( (maxElem / Math.pow(10, järk)) > 1 ) {

            for (int arv : sisend) {

                int räsi = paiskfunktsioon(arv);
                int i = p.get(räsi).size();

                p.sisesta(räsi, i, arv);

                if (i > 0) õigeLäbimäng.add(new Hinnang(new SisestamiseSamm<Integer>(0, räsi, i), RASKE_LISAMINE, null, true));
                else õigeLäbimäng.add(new Hinnang(new SisestamiseSamm<Integer>(0, räsi, i), LISAMINE, null, true));
            }

            sisend.clear();
            for (int i = 0; i < p.size(); i++) {
                while (p.get(i).size() > 0){
                    sisend.add(p.get(i, 0));
                    p.eemalda(i, 0);
                    õigeLäbimäng.add(new Hinnang(new EemaldamiseSamm<Integer>(sisend.size()-1, i, 0), EEMALDAMINE, null, true));
                }
            }

            järk++;
        }

        õigeLäbimäng.add(new Hinnang(new LõpetamiseSamm(), LÕPP, null, true));

        järk = 0;
        sisestamine = true;
        return õigeLäbimäng;
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Järjestada ahel positsioonimeetodil: " + sisend.toString();
    }

    /**
     * Oleku muutmine, kas on vaja elemente sisestada paisktabelisse või sealt eemaldada.
     * @param läbimäng Jooksev läbimäng.
     * @param hinnang Viimase sammu hinnang.
     */
    @Override
    public void astu(Läbimäng<Integer> läbimäng, Hinnang hinnang) {
        if (läbimäng.getAbijärjend().size() == 0 && sisestamine) {
            sisestamine = false;
        }
        if (läbimäng.getAbijärjend().size() == sisend.size() && !sisestamine) {
            sisestamine = true;
            järk++;
        }
    }

    /**
     * Oleku muutuse tagasivõtmine, kas on vaja elemente sisestada paisktabelisse või sealt eemaldada.
     * @param läbimäng Jooksev läbimäng.
     * @param hinnang Viimase sammu hinnang.
     */
    @Override
    public void tagasi(Läbimäng<Integer> läbimäng, Hinnang hinnang) {
        if (läbimäng.getAbijärjend().size() == 1 && !sisestamine
                && (hinnang.olek == LISAMINE || hinnang.olek == RASKE_LISAMINE)) {

            sisestamine = true;
        }
        if (läbimäng.getAbijärjend().size() == sisend.size()-1
                && sisestamine && hinnang.olek == EEMALDAMINE) {

            sisestamine = false;
            järk--;
        }
    }


    @Override
    public Hinnang hindaSammu(Samm samm, ArrayList<Integer> abijärjend, Paisktabel<Integer> paisktabel) {
        Samm õigeSamm = new LõpetamiseSamm();


        if (paisktabel.size() == 0) {
            õigeSamm = new PaisktabeliLoomiseSamm(abijärjend.size());
            return new Hinnang(õigeSamm, TABELI_LOOMINE, samm, õigeSamm.equals(samm));
        }

        if ((maxElem / Math.pow(10, järk)) > 1) {


            if (abijärjend.size() > 0 && sisestamine) {
                int arv = abijärjend.get(0);

                int räsi = paiskfunktsioon(arv);
                int koht = paisktabel.get(räsi).size();

                õigeSamm = new SisestamiseSamm<Integer>(0, räsi, koht);

                if (koht == 0) { // Lisatakse tühja kimpu
                    return new Hinnang(õigeSamm, LISAMINE, samm, õigeSamm.equals(samm));
                } else
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


        }
        // algoritm lõpetab
        return new Hinnang(õigeSamm, LÕPP, samm, õigeSamm.equals(samm));


    }

}
