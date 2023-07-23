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

        Läbimäng context = new Läbimäng();
        context.setHindaja(new Hindaja());

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
                context.setYlesanne(new LisamiseYlesanne(System.getProperty("user.dir") + "/sisendid/lisamineEemaldamine/sisend.txt"));
                break;
            case "e":
                context.setYlesanne(new EemaldamiseYlesanne( System.getProperty("user.dir") + "/sisendid/lisamineEemaldamine/sisend.txt"));
                break;
            case "k":
                context.setYlesanne(new KimbuYlesanne(System.getProperty("user.dir") + "/sisendid/kimbumeetod/sisend.txt"));
                break;
            case "p":
                context.setYlesanne(new PositsiooniYlesanne(System.getProperty("user.dir") + "/sisendid/positsioonimeetod/sisend.txt"));
        }

        System.out.println(context.ylesandeKirjeldus());

        while (true){
            if (alusta) {
                System.out.println("ALGUS");
                // uuesti alustamine
                while (true) {
                    try {
                        switch (ylesandetüüp) {
                            case "p":
                                System.out.print("Sisesta paisktabeli pikkus: ");
                                userCommand = sc.nextLine().split(" ");
                                context.astu(new PaisktabeliLoomiseSamm(Integer.parseInt(userCommand[0])));
                                break;
                            case "k":
                                System.out.print("Sisesta a b m (eraldatud tühikutega): ");
                                userCommand = sc.nextLine().split(" ");
                                context.astu(new PaisktabeliLoomiseSamm(Float.valueOf(userCommand[0]), Float.valueOf(userCommand[1]), Integer.parseInt(userCommand[2])));
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
            System.out.println("järjend: " + context.getAbijärjend().toString());
            System.out.println("paisktabel: " + context.getPaisktabel().toString());
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
                        context.astu(new LõpetamiseSamm());
                        System.out.println("Hinne: " + context.getPunktid() + "%");
                        return;

                    // s <i> <v> sisesta element massiivist indeksilt i paisktabelisse võtmele v
                    case "s":
                        if (userCommand.length > 3)
                            context.astu(new SisestamiseSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[3])));
                        else
                            context.astu(new SisestamiseSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), 0));
                        break;

                    // e <i> <v> eemalda paisktabelist võtmelt v element ja pane see massiivi indeksile i
                    case "e":
                        if (userCommand.length > 3)
                            context.astu(new EemaldamiseSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[3])));
                        else
                            context.astu(new EemaldamiseSamm(Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), 0));
                        break;

                    // u võta samm tagasi
                    case "u":
                        // true kui sammude ajalugu saab tühjaks. küsitakse uuesti paisktabeli parameetreid kui vaja
                        alusta = context.tagasi();
                        break;
                    default:
                        System.out.println("Command not found!");
                }
            }catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }
    }
}
