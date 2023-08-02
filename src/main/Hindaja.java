package main;


import java.util.ArrayList;
import java.util.Stack;

public class Hindaja {

    public enum Olek {
        RASKE_LISAMINE,
        LISAMINE,
        TABELI_LOOMINE,
        LÕPP,
        EEMALDAMINE,
        KUSTUTAMINE;
    }

    /**
     * Hinde arvutamine.
     * @param läbimäng Tudengi läbimäng.
     * @param õigeLäbimäng Õige läbimäng.
     * @return
     */
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
