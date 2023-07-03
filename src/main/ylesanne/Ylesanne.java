package main.ylesanne;

import main.Paisktabel;
import main.samm.Samm;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Ylesanne<T> {
    // sammu tüübid
    static final int RASKEOP = 1;
    static final int LISAMINE = 2;
    static final int TABELIOP = 3;
    static final int LÕPP = 4;
    static final int EEMALDAMINE = 5;
    static final int KUSTUTAMINE = 6;

    private Ylesanne ylesanne;
    protected Paisktabel paisktabel;
    protected ArrayList<T> abiMassiiv;
    private ArrayList<Integer> õigedTüübid;

    private Stack<Samm> sammud;
    private Stack<Integer> sammuTüübid;

    private float hinne;



    public Ylesanne() {
        this.sammud = new Stack<>();
        this.sammuTüübid = new Stack<>();
    }


    public void määra(Ylesanne ylesanne, Paisktabel paisktabel, ArrayList<T> abiMassiiv) {
        this.ylesanne = ylesanne;
        this.paisktabel = paisktabel;
        this.abiMassiiv = abiMassiiv;
    }

    public void setÕigedTüübid(ArrayList<Integer> õigedTüübid) {
        this.õigedTüübid = õigedTüübid;
    }

    public Paisktabel getPaisktabel() {
        return paisktabel;
    }

    public ArrayList<T> getAbiMassiiv() {
        return abiMassiiv;
    }

    public void paisktabeliParameetrid(float minElem, float maxElem, int elementideArv) { // m a b
    }

    public void astu(Samm samm) {
        int hinnang = ylesanne.hindaSammu(samm);
        ylesanne.astuJärg(hinnang);
        sammuTüübid.push(hinnang);
        sammud.push(samm);
        samm.astu(this);

    }

    public boolean tagasi() {
        if (sammud.isEmpty()) return true;

        Samm samm = sammud.pop();
        if (samm.tagasi(this)) {
            int hinnang = sammuTüübid.pop();
            ylesanne.tagasiJärg(hinnang);
        }
        else
            sammud.push(samm);

        if (sammud.isEmpty()) return true;
        return false;
    }

    protected void astuJärg(int hinnang) {}
    protected void tagasiJärg(int hinnang) {}

    public void lõpeta() {
        hinne = ylesanne.arvutaHinne();
    }
    public float getHinne() {
        return hinne;
    }
    public int räsi(int arv) {return arv % paisktabel.size();}

    abstract public String ylesandeKirjeldus();
    abstract public Integer hindaSammu(Samm samm);

    public float arvutaHinne() {
        Stack<Integer> hinnangud = sammuTüübid;

        float punktideSumma = 0f;
        int maxPunktid = õigedTüübid.size();

        while (!hinnangud.isEmpty()) {
            int hinnang = hinnangud.pop();
            punktideSumma += 1;
        }
        return punktideSumma / maxPunktid * 100.0f;
    }


}
