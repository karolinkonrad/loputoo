package main;


import java.util.ArrayList;
import java.util.Stack;

public class Hindaja {
    // operatsiooni liik
    public static final int RASKEOP = 1;
    public static final int LISAMINE = 2;
    public static final int TABELIOP = 3;
    public static final int LÕPP = 4;
    public static final int EEMALDAMINE = 5;
    public static final int KUSTUTAMINE = 6;

    public float arvutaHinne(Stack<Hinnang> läbimäng, ArrayList<Hinnang> õigeLäbimäng) {

        float punktideSumma = 0f;
        int maxPunktid = õigeLäbimäng.size();

        while (!läbimäng.isEmpty()) {
            if (läbimäng.pop().õige)
                punktideSumma += 1;
        }
        return punktideSumma / maxPunktid * 100.0f;
    }
}
