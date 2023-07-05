package main;

import main.samm.EemaldusSamm;
import main.samm.LopetusSamm;
import main.samm.PaisktabeliLoomisSamm;
import main.samm.SisestusSamm;
import main.ylesanne.*;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        Ylesanne yl = null;
        Scanner sc = new Scanner(System.in);
        boolean alusta = true;
        String[] userCommand;

        // ülesande tüübi valimine
        System.out.println("""
                    l - lisamine
                    e - eemaldamine
                    k - kimbumeetod
                    p - positsioonimeetod""");

        System.out.print("Vali ülesande tüüp: ");
        String ylesandetüüp = sc.nextLine();

        switch (ylesandetüüp) {
            case "l":
                yl = new LisamiseYlesanne(System.getProperty("user.dir") + "/sisendid/lisamineEemaldamine/sisend.txt");
                break;
            case "e":
                yl = new EemaldamiseYlesanne( System.getProperty("user.dir") + "/sisendid/lisamineEemaldamine/sisend.txt");
                break;
            case "k":
                yl = new KimbuYlesanne(System.getProperty("user.dir") + "/sisendid/kimbumeetod/sisend.txt");
                break;
            case "p":
                yl = new PositsiooniYlesanne(System.getProperty("user.dir") + "/sisendid/positsioonimeetod/sisend.txt");
        }

        System.out.println(yl.ylesandeKirjeldus());

        while (true){
            if (alusta) {
                // uuesti alustamine
                while (true) {
                    try {
                        switch (ylesandetüüp) {
                            case "p":
                                System.out.print("Sisesta paisktabeli pikkus: ");
                                userCommand = sc.nextLine().split(" ");
                                yl.astu(new PaisktabeliLoomisSamm(Integer.parseInt(userCommand[0])));
                                break;
                            case "k":
                                System.out.print("Sisesta a b m (eraldatud tühikutega): ");
                                userCommand = sc.nextLine().split(" ");
                                yl.astu(new PaisktabeliLoomisSamm(Float.valueOf(userCommand[0]), Float.valueOf(userCommand[1]), Integer.parseInt(userCommand[2])));
                                break;
                            default:

                        }
                        break;
                    }catch (IllegalArgumentException e) {
                        throw e;
                    }
                }

                alusta = false;
            }

            System.out.println("-----------------------------------------------------------");
            System.out.println("massiiv: " + yl.getAbiMassiiv().toString());
            System.out.println("paisktabel: " + yl.getPaisktabel().toString());
            System.out.println("""
                    l - algoritm lõpetab
                    s <i> <r> - sisesta element massiivist indeksilt i paisktabelisse indeksile r 
                    e <i> <r> - eemalda paisktabelist indeksilt r element ja pane see massiivi indeksile i
                    u - võta samm tagasi""");

            userCommand = sc.nextLine().split(" ");

            try {
                switch (userCommand[0]) {
                    // l algoritm lõpetab
                    case "l":
                        yl.astu(new LopetusSamm());
                        System.out.println("Hinne: " + yl.getHinne() + "%");
                        return;

                    // s <i> <v> sisesta element massiivist indeksilt i paisktabelisse võtmele v
                    case "s":
                        if (userCommand.length > 3)
                            yl.astu(new SisestusSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[3])));
                        else
                            yl.astu(new SisestusSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), 0));
                        break;

                    // e <i> <v> eemalda paisktabelist võtmelt v element ja pane see massiivi indeksile i
                    case "e":
                        if (userCommand.length > 3)
                            yl.astu(new EemaldusSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[3])));
                        else
                            yl.astu(new EemaldusSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), 0));
                        break;

                    // u võta samm tagasi
                    case "u":
                        // true kui sammude ajalugu saab tühjaks. küsitakse uuesti paisktabeli parameetreid kui vaja
                        alusta = yl.tagasi();
                        break;
                }
            }catch (RuntimeException e) {
                throw e;
                //System.out.println(e);
            }
        }
    }
}
