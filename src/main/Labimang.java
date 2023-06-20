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
    private Stack<Integer> hinnangud;
    private float hinne;


    public Labimang(Ylesanne ylesanne) {
        this.paisktabel = ylesanne.algusPaisktabel();
        this.sisend = ylesanne.algusMassiiv();
        this.ylesanne = ylesanne;
        this.sammud = new Stack<>();
        this.hinnangud = new Stack<>();
    }

    public void astu(Samm samm) {
        int hinnang = ylesanne.hindaSammu(this, samm);
        hinnangud.push(hinnang);
        sammud.push(samm);
        samm.astu();
    }

    public boolean tagasi() {
        Samm samm = sammud.pop();
        if (samm.tagasi())
            hinnangud.pop();
        else
            sammud.push(samm);

        if (sammud.isEmpty()) return true;
        return false;
    }

    public Paisktabel getPaisktabel() {
        return paisktabel;
    }

    public ArrayList<Integer> getSisend() {
        return sisend;
    }

    public Stack<Integer> getHinnangud() {
        return hinnangud;
    }

    public void lõpeta() {
        hinne = ylesanne.arvutaHinne(this);
    }

    public float getHinne() {
        return hinne;
    }
}

