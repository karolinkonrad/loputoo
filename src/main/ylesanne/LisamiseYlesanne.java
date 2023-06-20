package main.ylesanne;

import main.samm.LopetusSamm;
import main.samm.PaisktabeliLoomisSamm;
import main.samm.Samm;
import main.samm.SisestusSamm;
import main.Labimang;
import main.Paisktabel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class LisamiseYlesanne extends Ylesanne {

    public LisamiseYlesanne() throws IOException {
        super();
        File folder = new File( System.getProperty("user.dir") + "/sisendid/lisamineEemaldamine");
        File[] listOfFiles = folder.listFiles();
        ArrayList<Integer> sisend = new ArrayList<>();

        for (File file : listOfFiles) {
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
        super.setSisend(sisend);
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Lisa lahtise adresseerimisega paisktabelisse samas järjekorras järgmised kirjed: " + super.getSisend().toString();
    }

    @Override
    public Integer hindaSammu(Labimang labimang, Samm samm) {
        // tagastab vea tüübi

        Samm õigeSamm = new LopetusSamm(labimang);

        if (labimang.getPaisktabel().size() == 0) {
            õigeSamm = new PaisktabeliLoomisSamm(labimang, labimang.getSisend().size());
            return õigeSamm.equals(samm) ? 3: -3;
        }

        else if (labimang.getSisend().size() > 0) { // veel on lisamata kirjeid
            int arv = labimang.getSisend().get(0);

            int voti = arv % labimang.getPaisktabel().size();
            int koht = labimang.getPaisktabel().leiaVabaKoht(voti);
            õigeSamm = new SisestusSamm(labimang, koht, arv);

            if (koht == voti) { // edasivaatamisi ei tehta
                return õigeSamm.equals(samm) ? 2: -2;
            }
            else
                return õigeSamm.equals(samm) ? 1: -1;

        }
        // algoritm lõpetab
        return õigeSamm.equals(samm) ? 4: -4;
    }

    @Override
    public float arvutaHinne(Labimang labimang) {
        Stack<Integer> hinnangud = labimang.getHinnangud();
        float punktideSumma = 0.0f;
        int maxPunktid = hinnangud.size();

        while (!hinnangud.isEmpty()) {
            int hinnang = hinnangud.pop();
            if (hinnang > 0) {
                punktideSumma += 1;
            }
        }
        return punktideSumma / maxPunktid * 100.0f;
    }

}
