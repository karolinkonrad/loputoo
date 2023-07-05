package main;

import java.io.*;

public class Logija {
    private File fail;

    public Logija() throws IOException {
        String dir = System.getProperty("user.dir") + "/läbimängud";
        fail = File.createTempFile("log", ".txt", new File(dir));
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
