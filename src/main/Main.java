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
        Labimang labimang = null;
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
                yl = new LisamiseYlesanne();
                break;
            case "e":
                yl = new EemaldamiseYlesanne();
                break;
            case "k":
                yl = new KimbuYlesanne();
                break;
            case "p":
                yl = new PositsiooniYlesanne();
        }

        labimang = new Labimang(yl);
        System.out.println(yl.ylesandeKirjeldus());

        while (true){
            if (alusta) {
                // uuesti alustamine
                while (true) {
                    try {
                        switch (ylesandetüüp) {
                            case "e":
                                break;
                            case "k":
                                System.out.print("Sisesta a b m (eraldatud tühikutega): ");
                                userCommand = sc.nextLine().split(" ");
                                yl.paisktabeliParameetrid(Integer.parseInt(userCommand[0]), Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]));
                                labimang.astu(new PaisktabeliLoomisSamm(labimang, Integer.parseInt(userCommand[2])));
                                break;
                            default:
                                System.out.print("Sisesta paisktabeli pikkus: ");
                                userCommand = sc.nextLine().split(" ");
                                labimang.astu(new PaisktabeliLoomisSamm(labimang, Integer.parseInt(userCommand[0])));

                        }
                        break;
                    }catch (RuntimeException e) {
                        System.out.println(e);
                    }
                }

                alusta = false;
            }

            System.out.println("-----------------------------------------------------------");
            System.out.println("abimasiiv: " + labimang.getSisend().toString());
            System.out.println("paisktabel: \n" + labimang.getPaisktabel().toString());
            System.out.println("""
                    l - algoritm lõpetab
                    s <x> <i> sisesta x indeksile i
                    e <i> <x> kustuta indexilt i arv x
                    u võta samm tagasi""");

            userCommand = sc.nextLine().split(" ");

            try {
                switch (userCommand[0]) {
                    // l algoritm lõpetab
                    case "l":
                        labimang.astu(new LopetusSamm(labimang));
                        System.out.println("Hinne:" + labimang.getHinne());
                        return;
                    // s <x> <i> sisesta x indeksile i
                    case "s":
                        labimang.astu(new SisestusSamm(labimang, Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[1])));
                        break;
                    // e <i> <x> kustuta indexilt i arv x
                    case "e":
                        labimang.astu(new EemaldusSamm(labimang, Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2])));
                        break;
                    // u võta samm tagasi
                    case "u":
                        alusta = labimang.tagasi();
                        break;
                }
            }catch (RuntimeException e) {
                System.out.println(e);
            }
        }
    }
}
