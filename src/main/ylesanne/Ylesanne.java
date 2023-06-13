package main.ylesanne;

import main.Labimang;
import main.Paisktabel;
import main.samm.Samm;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Ylesanne {
    private Stack<Integer> hinnangud;
    private ArrayList<Integer> sisend;

    public Ylesanne() {
        this.hinnangud = new Stack<>();
        this.sisend = new ArrayList<>();
    }

    public ArrayList<Integer> getSisend() {
        return sisend;
    }

    public void setSisend(ArrayList<Integer> sisend) {
        this.sisend = sisend;
    }

    abstract public ArrayList<Integer> ylSisend();
    abstract public Paisktabel getPaisktabel();
    abstract public String ylesandeKirjeldus();
    abstract public void paisktabeliParameetrid(int parseInt, int parseInt1, int parseInt2); // m a b
    abstract public Integer hinda(Labimang labimang, Samm samm);

    public int maxPunktid() {
        return sisend.size();
    }

    public void astu(int hinnang) {
        hinnangud.push(hinnang);
    }

    public void tagasi() {
        hinnangud.pop();
    }

    public Stack<Integer> getHinnangud() {
        return hinnangud;
    }
}
