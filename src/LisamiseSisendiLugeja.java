import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LisamiseSisendiLugeja {

    ArrayList<ArrayList<Integer>> sisendid;

    public LisamiseSisendiLugeja() throws FileNotFoundException {
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

    public ArrayList<Integer> getSisend() {
        return sisendid.get((int)(Math.random() * sisendid.size()));
    }
}
