package main;

import main.samm.Samm;
import main.ylesanne.Ylesanne;

import java.util.ArrayList;
import java.util.Stack;

public class Labimang {
    private Paisktabel paisktabel;
    private ArrayList<Integer> sisend;
    private Ylesanne ylesanne;
    private Stack<Samm> sammud;


    public Labimang(Ylesanne ylesanne) {
        this.paisktabel = ylesanne.getPaisktabel();
        this.sisend = ylesanne.ylSisend();
        this.ylesanne = ylesanne;
        this.sammud = new Stack<>();
        }

    public void astu(Samm samm) {
        int hinnang = ylesanne.hinda(this, samm);
        ylesanne.astu(hinnang);
        sammud.push(samm);
        samm.astu();
    }

    public boolean tagasi() {
        ylesanne.tagasi();
        Samm samm = sammud.pop();
        samm.tagasi();

        if (sammud.isEmpty()) return true;
        return false;
    }

    public Paisktabel getPaisktabel() {
        return paisktabel;
    }

    public ArrayList<Integer> getSisend() {
        return sisend;
    }

    public float getHinne() {
        Stack<Integer> hinnangud = ylesanne.getHinnangud();
        float punktideSumma = 0.0f;
        int maxPunktid = hinnangud.size();

        while (!hinnangud.isEmpty()) {
            int hinnang = hinnangud.pop();
            if (hinnang > 0) {
                punktideSumma += 1;
            }
        }
        return punktideSumma / maxPunktid * 100.0f;
    }

}

