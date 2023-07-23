package main.ylesanne;

import main.Hinnang;
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

import static main.Hindaja.*;

public class LisamiseYlesanne extends Ylesanne {

    private ArrayList<Integer> sisend;
    private int kompesamm;

    public LisamiseYlesanne(String faili_tee) throws IOException {
        loeSisend(faili_tee);

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
            kompesamm = 1;
        }
    }

    @Override
    public Paisktabel getPaisktabel() {
        return new Paisktabel(kompesamm, sisend.size());
    }

    @Override
    public ArrayList getAbijärjend() {
        return (ArrayList) sisend.clone();
    }

    @Override
    public void paisktabeliParameetrid(float minElem, float maxElem, int elementideArv) {

    }

    @Override
    public ArrayList<Hinnang> leiaÕigeLäbimäng() {
        // õige läbimängu sammude leidmine
        Paisktabel p = getPaisktabel();

        ArrayList<Hinnang> õigeLäbimäng = new ArrayList<>();

        for (Integer arv : sisend) {
            int räsi = paiskfunktsioon(arv, p);
            int koht = p.leiaVabaKoht(räsi);

            if (räsi != koht)
                õigeLäbimäng.add(new Hinnang(new SisestamiseSamm(0, koht, 0), RASKEOP, null, true));
            else õigeLäbimäng.add(new Hinnang(new SisestamiseSamm(0, koht, 0), LISAMINE, null, true));

            p.sisesta(koht, 0, arv);
        }

        õigeLäbimäng.add(new Hinnang(new LõpetamiseSamm(), LÕPP, null, true));
        return õigeLäbimäng;
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Olgu lahtise adresseerimisega paisktabelil jääkpaiskamine, kompesamm " + kompesamm + " ja ridu " + sisend.size()
                + ".\nLisa paisktabelisse samas järjekorras järgmised elemendid: " + sisend.toString();
    }

    @Override
    public void astu(Hinnang hinnang) {

    }

    @Override
    public void tagasi(Hinnang hinnang) {

    }

    @Override
    public Hinnang hindaSammu(Samm samm, ArrayList abijärjend, Paisktabel paisktabel) {
        // tagastab vea tüübi

        Samm õigeSamm = new LõpetamiseSamm();

        if (abijärjend.size() > 0) { // veel on lisamata kirjeid
            int arv = (int) abijärjend.get(0);

            int räsi = paiskfunktsioon(arv, paisktabel);
            int vabaRäsi = paisktabel.leiaVabaKoht(räsi);
            õigeSamm = new SisestamiseSamm(0, vabaRäsi, 0);

            if (vabaRäsi == räsi) { // nihutamisi ei tehta
                return new Hinnang(õigeSamm, LISAMINE, samm, õigeSamm.equals(samm));
            }
            else
                return new Hinnang(õigeSamm, RASKEOP, samm, õigeSamm.equals(samm));

        }
        // algoritm lõpetab
        return new Hinnang(õigeSamm, LÕPP, samm, õigeSamm.equals(samm));
    }

}
