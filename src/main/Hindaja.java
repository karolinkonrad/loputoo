package main;


import java.util.ArrayList;
import java.util.Stack;

public class Hindaja {
    public float arvutaHinne(Stack<Integer> läbimäng, ArrayList<Integer> õigeLäbimäng) {

        float punktideSumma = 0f;
        int maxPunktid = õigeLäbimäng.size();

        while (!läbimäng.isEmpty()) {
            int liik = läbimäng.pop();
            if (liik > 0)
                punktideSumma += 1;
        }
        return punktideSumma / maxPunktid * 100.0f;
    }
}
