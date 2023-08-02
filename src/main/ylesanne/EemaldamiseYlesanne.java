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
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static main.Hindaja.Olek.*;


public class EemaldamiseYlesanne extends Ylesanne<Integer>{
    private ArrayList<Integer> sisend;

    private int eemaldatav;
    private int eemaldatavaRäsi;
    private HashMap<Integer, Integer> kompejadaAlgsedIndeksid;
    private int järg;
    private int kompesamm;


    public EemaldamiseYlesanne(String faili_tee) throws IOException {
        loeSisend(faili_tee);
    }

    /**
     * Failist ülesande andmete lugemine.
     * Rakendatakse konstruktoris. Salvestab sisendi, kompesammu ja eemaldatava.
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

            for (String s : rida.split(" ")) {
                if (s.contains("*"))
                    eemaldatav = Integer.parseInt(s.replaceAll("[\\[*\\]]",""));
                sisend.add(Integer.parseInt(s.replaceAll("[\\[*\\]]","")));
            }
            kompesamm = 1;
        }
    }

    /**
     * Algse paisktabeli andmine.
     * @return Uus paisktabel, mis on täidetud arvudega sisendist.
     */
    @Override
    public Paisktabel<Integer> getPaisktabel() {
        Paisktabel<Integer> paisktabel = new Paisktabel<>(kompesamm, sisend.size());
        for (Integer arv : sisend) {
            paisktabel.sisesta( paisktabel.leiaVabaKoht(paiskfunktsioon(arv, paisktabel)), 0, arv);
        }
        return paisktabel;
    }

    /**
     * Algse abijärjendi andmine.
     * @return Uus tühi järjend.
     */
    @Override
    public ArrayList<Integer> getAbijärjend() {
        return new ArrayList<>();
    }

    //pole vajalik
    @Override
    public void setPaisktabeliParameetrid(float minElem, float maxElem, int elementideArv) {
    }

    @Override
    public ArrayList<Hinnang> leiaÕigeLäbimäng() {

        Paisktabel<Integer> p = getPaisktabel();

        kompejadaAlgsedIndeksid = new HashMap<>();
        ArrayList<Hinnang> õigeLäbimäng = new ArrayList<>();

        eemaldatavaRäsi = p.leiaAsukoht(eemaldatav, paiskfunktsioon(eemaldatav, p));
        p.eemalda(eemaldatavaRäsi, 0);

        õigeLäbimäng.add(new Hinnang(new EemaldamiseSamm<Integer>(0, eemaldatavaRäsi, 0), KUSTUTAMINE, null, true));

        int i = eemaldatavaRäsi;

        while (true){ // kompejada läbimine
            i++;
            if (i >= p.size()) i = 0;
            if (i == eemaldatavaRäsi) break;
            if (p.get(i, 0) == null) break;

            int arv = p.get(i, 0);
            kompejadaAlgsedIndeksid.put(arv, i);
            p.eemalda(i, 0);
            õigeLäbimäng.add(new Hinnang(new EemaldamiseSamm<Integer>(0, i, 0), EEMALDAMINE, null, true));

            int uusKoht = p.leiaVabaKoht(paiskfunktsioon(arv, p));
            if (uusKoht != i)
                õigeLäbimäng.add(new Hinnang(new SisestamiseSamm<Integer>(0, uusKoht, 0), RASKE_LISAMINE, null, true));
            else õigeLäbimäng.add(new Hinnang(new SisestamiseSamm<Integer>(0, uusKoht, 0), LISAMINE, null, true));
            p.sisesta(uusKoht, 0, arv);

        }
        õigeLäbimäng.add(new Hinnang(new LõpetamiseSamm(), LÕPP, null, true));

        järg = eemaldatavaRäsi;

        return õigeLäbimäng;
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Olgu lahtise adresseerimisega paisktabelil jääkpaiskamine, kompesamm " + kompesamm + " ja ridu " + sisend.size()
                + ".\nEemalda lahtise adresseerimiesega paisktabelist " + eemaldatav;
    }

    /**
     * Kompejada järje edasi viimine.
     * @param läbimäng Jooksev läbimäng.
     * @param hinnang Viimase sammu hinnang.
     */
    @Override
    public void astu(Läbimäng<Integer> läbimäng, Hinnang hinnang) {
        if ((hinnang.olek == EEMALDAMINE || hinnang.olek == KUSTUTAMINE) && hinnang.õige)
            järg++;
    }

    /**
     * Kompejada järje tagasivõtmine.
     * @param läbimäng Jooksev läbimäng.
     * @param hinnang Viimase sammu hinnang.
     */
    @Override
    public void tagasi(Läbimäng<Integer> läbimäng, Hinnang hinnang) {
        if ((hinnang.olek == EEMALDAMINE || hinnang.olek == KUSTUTAMINE) && hinnang.õige)
            järg--;
    }

    @Override
    public Hinnang hindaSammu(Samm samm, ArrayList<Integer> abijärjend, Paisktabel<Integer> paisktabel) {
        Samm õigeSamm = new LõpetamiseSamm();


        if (abijärjend.size() == 0) { // kas eemaldatav on eemaldatud?
            int eemaldatavaVõti = paisktabel.leiaAsukoht(eemaldatav, paiskfunktsioon(eemaldatav, paisktabel));
            õigeSamm = new EemaldamiseSamm<Integer>(0, eemaldatavaVõti, 0);

            return new Hinnang(õigeSamm, KUSTUTAMINE, samm, õigeSamm.equals(samm));
        }

        // kompejada läbimine

        if (abijärjend.size() > 1) { // kas on kirjeid tagasi panna?
            int arv = abijärjend.get(0);
            int räsi = paiskfunktsioon(arv, paisktabel);
            int vabaRäsi = paisktabel.leiaVabaKoht(räsi);

            õigeSamm = new SisestamiseSamm<Integer>(0, vabaRäsi, 0);

            if (vabaRäsi != kompejadaAlgsedIndeksid.get(arv)) { // kas uus vabaRäsi on erinev vanast võtmest?
                return new Hinnang(õigeSamm, RASKE_LISAMINE, samm, õigeSamm.equals(samm));
            }
            else
                return new Hinnang(õigeSamm, LISAMINE, samm, õigeSamm.equals(samm));
        }

        // kas on kirjeid vales kohas?

        if (järg >= paisktabel.size()) järg = 0;
        if (järg == eemaldatavaRäsi || paisktabel.get(järg).size() == 0)
            // algoritm lõpetab 
            return new Hinnang(õigeSamm, LÕPP, samm, õigeSamm.equals(samm));

        õigeSamm = new EemaldamiseSamm<Integer>(0, järg, 0);
        return new Hinnang(õigeSamm, EEMALDAMINE, samm, õigeSamm.equals(samm));
        
    }

}
