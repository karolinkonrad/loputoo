package main;

import main.samm.EemaldamiseSamm;
import main.samm.LõpetamiseSamm;
import main.samm.PaisktabeliLoomiseSamm;
import main.samm.SisestamiseSamm;
import main.ylesanne.*;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        Läbimäng läbimäng = new Läbimäng();
        läbimäng.setHindaja(new Hindaja());

        Scanner sc = new Scanner(System.in);
        boolean alusta = true;
        String[] userCommand;



        while (true){
            if (alusta) {
                System.out.println("ALGUS");
                // uuesti alustamine
                while (true) {
                    // ülesande tüübi valimine
                    System.out.println("""
                    l - lisamine
                    e - eemaldamine
                    k - kimbumeetod
                    p - positsioonimeetod
                    x - välju""");

                    System.out.print("Vali ülesande tüüp: ");
                    String ylesandetüüp = sc.nextLine();

                    switch (ylesandetüüp) {
                        case "l":
                            läbimäng.setYlesanne(new LisamiseYlesanne(System.getProperty("user.dir") + "/sisendid/lisamineEemaldamine/sisend.txt"));
                            break;
                        case "e":
                            läbimäng.setYlesanne(new EemaldamiseYlesanne( System.getProperty("user.dir") + "/sisendid/lisamineEemaldamine/sisend.txt"));
                            break;
                        case "k":
                            läbimäng.setYlesanne(new KimbuYlesanne(System.getProperty("user.dir") + "/sisendid/kimbumeetod/sisend.txt"));
                            break;
                        case "p":
                            läbimäng.setYlesanne(new PositsiooniYlesanne(System.getProperty("user.dir") + "/sisendid/positsioonimeetod/sisend.txt"));
                            break;
                        case "x":
                            return;
                    }

                    System.out.println(läbimäng.ylesandeKirjeldus());

                    try {
                        switch (ylesandetüüp) {
                            case "p":
                                System.out.print("Sisesta paisktabeli pikkus: ");
                                userCommand = sc.nextLine().split(" ");
                                läbimäng.astu(new PaisktabeliLoomiseSamm(Integer.parseInt(userCommand[0])));
                                break;
                            case "k":
                                System.out.print("Sisesta a b m (eraldatud tühikutega): ");
                                userCommand = sc.nextLine().split(" ");
                                läbimäng.astu(new PaisktabeliLoomiseSamm(Float.parseFloat(userCommand[0]), Float.parseFloat(userCommand[1]), Integer.parseInt(userCommand[2])));
                                break;
                            default:

                        }
                        break;
                    }catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                }

                alusta = false;
            }

            System.out.println("-----------------------------------------------------------");
            System.out.println("järjend: " + läbimäng.getAbijärjend().toString());
            System.out.println("paisktabel: " + läbimäng.getPaisktabel().toString());
            System.out.println("""
                    l - algoritm lõpetab
                    s <i> <r> <k> - sisesta element massiivist indeksilt i paisktabelisse indeksile r kohale k
                    e <i> <r> <k> - eemalda paisktabelist realt r kohalt k element ja pane see massiivi indeksile i
                    u - võta samm tagasi""");

            userCommand = sc.nextLine().split(" ");

            try {
                switch (userCommand[0]) {
                    // l algoritm lõpetab
                    case "l":
                        läbimäng.astu(new LõpetamiseSamm());
                        System.out.println("Hinne: " + läbimäng.getPunktid() + "%");
                        alusta = true;
                        break;

                    // s <i> <r> <k> - sisesta element massiivist indeksilt i paisktabelisse indeksile r kohale k
                    case "s":
                        if (userCommand.length > 3)
                            läbimäng.astu(new SisestamiseSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[3])));
                        else
                            läbimäng.astu(new SisestamiseSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), 0));
                        break;

                    // e <i> <r> <k> - eemalda paisktabelist realt r kohalt k element ja pane see massiivi indeksile i
                    case "e":
                        if (userCommand.length > 3)
                            läbimäng.astu(new EemaldamiseSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[3])));
                        else
                            läbimäng.astu(new EemaldamiseSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), 0));
                        break;

                    // u võta samm tagasi
                    case "u":
                        // true kui sammude ajalugu saab tühjaks. küsitakse uuesti paisktabeli parameetreid kui vaja
                        alusta = läbimäng.tagasi();
                        break;
                    default:
                        System.out.println("Käsku ei leitud.");
                }
            }catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }
    }
}
