package main.ylesanne;

import main.Labimang;
import main.Paisktabel;
import main.samm.Samm;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Ylesanne {
    private ArrayList<Integer> sisend;
    private int kompesamm;
    // private Räsifunktsioon räsifunktsioon; TODO: kompesamm ja räsifunktsioon ja muud asjad võiks olla lihtsaasti muudetavad näiteks loetakse failist

    public Ylesanne() {
        this.sisend = new ArrayList<>();
    } // TODO: failist lugemine siin?

    public ArrayList<Integer> getSisend() {
        return sisend;
    }

    public void setSisend(ArrayList<Integer> sisend) {
        this.sisend = sisend;
    }

    public ArrayList<Integer> algusMassiiv() {
        return (ArrayList<Integer>) this.getSisend().clone();
    }

    public Paisktabel algusPaisktabel() {
        return new Paisktabel();
    }

    public void paisktabeliParameetrid(int parseInt, int parseInt1, int parseInt2) { // m a b
    }

    abstract public String ylesandeKirjeldus();
    abstract public Integer hindaSammu(Labimang labimang, Samm samm);
    abstract public float arvutaHinne(Labimang labimang);
}
