package main.ylesanne;

import main.Hinnang;
import main.Läbimäng;
import main.Paisktabel;
import main.samm.LõpetamiseSamm;
import main.samm.Samm;
import main.samm.SisestamiseSamm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static main.Hindaja.Olek.*;

public class LisamiseYlesanne extends Ylesanne<Integer> {

    private ArrayList<Integer> sisend;
    private int kompesamm;

    public LisamiseYlesanne(String faili_tee) throws IOException {
        loeSisend(faili_tee);

    }

    /**
     * Failist ülesande andmete lugemine.
     * Rakendatakse konstruktoris. Salvestab sisendi ja kompesammu.
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
                sisend.add(Integer.parseInt(s.replaceAll("[\\[*\\]]","")));
            }
            kompesamm = 1;
        }
    }

    /**
     * Algse paisktabeli andmine.
     * @return Uus paisktabel, milles on sama palju ridu, kui on sisendi pikkus.
     */
    @Override
    public Paisktabel<Integer> getPaisktabel() {
        return new Paisktabel<>(kompesamm, sisend.size());
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
        Paisktabel<Integer> p = getPaisktabel();

        ArrayList<Hinnang> õigeLäbimäng = new ArrayList<>();

        for (Integer arv : sisend) {
            int räsi = paiskfunktsioon(arv, p);
            int koht = p.leiaVabaKoht(räsi);

            if (räsi != koht)
                õigeLäbimäng.add(new Hinnang(new SisestamiseSamm<Integer>(0, koht, 0), RASKE_LISAMINE, null, true));
            else õigeLäbimäng.add(new Hinnang(new SisestamiseSamm<Integer>(0, koht, 0), LISAMINE, null, true));

            p.sisesta(koht, 0, arv);
        }

        õigeLäbimäng.add(new Hinnang(new LõpetamiseSamm(), LÕPP, null, true));
        return õigeLäbimäng;
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Olgu lahtise adresseerimisega paisktabelil jääkpaiskamine, kompesamm " + kompesamm + " ja ridu " + sisend.size()
                + ".\nLisa paisktabelisse samas järjekorras järgmised elemendid: " + sisend;
    }

    //pole vajalik
    @Override
    public void astu(Läbimäng<Integer> läbimäng, Hinnang hinnang) {

    }

    //pole vajalik
    @Override
    public void tagasi(Läbimäng<Integer> läbimäng, Hinnang hinnang) {

    }

    @Override
    public Hinnang hindaSammu(Samm samm, ArrayList<Integer> abijärjend, Paisktabel<Integer> paisktabel) {

        Samm õigeSamm = new LõpetamiseSamm();

        if (abijärjend.size() > 0) { // veel on lisamata kirjeid
            int arv = abijärjend.get(0);

            int räsi = paiskfunktsioon(arv, paisktabel);
            int vabaRäsi = paisktabel.leiaVabaKoht(räsi);
            õigeSamm = new SisestamiseSamm<Integer>(0, vabaRäsi, 0);

            if (vabaRäsi == räsi) { // nihutamisi ei tehta
                return new Hinnang(õigeSamm, LISAMINE, samm, õigeSamm.equals(samm));
            }
            else
                return new Hinnang(õigeSamm, RASKE_LISAMINE, samm, õigeSamm.equals(samm));

        }
        // algoritm lõpetab
        return new Hinnang(õigeSamm, LÕPP, samm, õigeSamm.equals(samm));
    }

}
