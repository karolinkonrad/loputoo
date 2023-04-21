import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LisamiseYlesanne implements Ylesanne {

    ArrayList<ArrayList<Integer>> sisendid;

    public LisamiseYlesanne() throws FileNotFoundException {
        File folder = new File( System.getProperty("user.dir") + "/sisendid");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {

                try (Scanner sc = new Scanner(file, "utf-8")){
                    this.sisendid = new ArrayList<>();

                    while (sc.hasNextLine()) {
                        String ridastr = sc.nextLine();
                        ArrayList<Integer> sisendList = new ArrayList<>();

                        for (String s : ridastr.split(" ")) {
                            sisendList.add(Integer.parseInt(s.replaceAll("[\\[*\\]]","")));
                        }
                        this.sisendid.add(sisendList);
                    }

                }
            }

        }
    }

    @Override
    public ArrayList<Integer> getSisend() {
        return sisendid.get((int)(Math.random() * sisendid.size()));
    }

    @Override
    public String ylesandeKirjeldus() {
        return "Lisa kirjed lahtise adresseerimisega paisktabelisse.";
    }

    @Override
    public Samm arvutaJargmineSamm(Labimang labimang) {
        if (labimang.getPaisktabelid().size() == 0) {
            return new PaisktabeliLoomisSamm(labimang, labimang.getSisend().size());
        }
        if (labimang.getSisend().size() > 0) {
            int arv = labimang.getSisend().get(0);
            int tabel = 0;
            int koht = labimang.getPaisktabelid().get(tabel).leiaVabaKoht(arv);
            return new SisestusSamm(labimang, tabel, koht, arv);
        }
        return new LopetusSamm(labimang);
    }

    @Override
    public int maxPunktid() {
        return sisendid.get(0).size();
    }
}
