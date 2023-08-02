package main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logija {
    private final File fail;

    public Logija() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-hhmmss.SSS");
        String dir = System.getProperty("user.dir") + "/läbimängud/";

        File theDir = new File(dir);
        if (!theDir.exists()){
            theDir.mkdirs();
        }

        fail = new File( dir + sdf.format( new Date() ) + ".txt");
    }

    /**
     * Teksti kirnutamine faili.
     * Kasutusel logimiseks.
     * @param sisu Antud sisu.
     */
    public void logi(String sisu) {
        try (FileWriter fw = new FileWriter("läbimängud/"+ fail.getName(), true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)){

            pw.println(sisu);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
