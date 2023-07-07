package main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logija {
    private File fail;

    public Logija() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-hhmmss.SSS");
        String dir = System.getProperty("user.dir") + "/läbimängud/";

        fail = new File( dir + sdf.format( new Date() ) + ".txt");
    }

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
