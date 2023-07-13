package main.ylesanne;

import main.Hindaja;
import main.Hinnang;
import main.Logija;
import main.Paisktabel;
import main.samm.Samm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public abstract class Ylesanne<T> {

    private Stack<Hinnang> läbimäng;
    private Logija logija;
    protected Hindaja hindaja;

    private Ylesanne ylesanne;
    protected Paisktabel<T> paisktabel;
    protected ArrayList<T> abiMassiiv;
    private ArrayList<Hinnang> õigeLäbimäng;

    private float punktid;

    public Ylesanne(Hindaja hindaja) throws IOException {
        this.läbimäng = new Stack<>();
        this.logija = new Logija();
        this.hindaja = hindaja;
    }


    public void määra(Ylesanne ylesanne, Paisktabel paisktabel, ArrayList<T> abiMassiiv) {
        this.ylesanne = ylesanne;
        this.paisktabel = paisktabel;
        this.abiMassiiv = abiMassiiv;

        logija.logi(ylesanne.ylesandeKirjeldus());

    }

    public void setÕigeLäbimäng(ArrayList<Hinnang> õigeLäbimäng) {
        this.õigeLäbimäng = õigeLäbimäng;
    }
    public Paisktabel getPaisktabel() {
        return paisktabel;
    }
    public ArrayList<T> getAbiMassiiv() {
        return abiMassiiv;
    }
    public float getPunktid() {
        return punktid;
    }
    public void paisktabeliParameetrid(float minElem, float maxElem, int elementideArv) { // m a b
    }

    public void astu(Samm samm) {
        Hinnang hinnang = ylesanne.hindaSammu(samm);
        ylesanne.astuJärg(hinnang);
        läbimäng.push(hinnang);

        logija.logi("---------------------------------------------------------\n"
                + abiMassiiv.toString() + "\n"
                + paisktabel.toString() + "\n"
                + hinnang.toString() );

        samm.astu(this);
    }

    public boolean tagasi() {

        if (läbimäng.isEmpty()) return true;

        Hinnang hinnang = läbimäng.pop();
        if (hinnang.tudengiSamm.tagasi(this)) {
            ylesanne.tagasiJärg(hinnang);
            logija.logi("---------------------------------------------------------\n"
                    + abiMassiiv.toString() + "\n"
                    + paisktabel.toString() + "\nTAGASI");
        }
        else
            läbimäng.push(hinnang);

        if (läbimäng.isEmpty()) return true;
        return false;
    }

    public void lõpeta() {
        punktid = hindaja.arvutaHinne(läbimäng, õigeLäbimäng);
        logija.logi("###########################\nHinne: " + punktid + "%\n");
    }

    protected void astuJärg(Hinnang hinnang) {}
    protected void tagasiJärg(Hinnang hinnang) {}

    public int paiskfunktsioon(int arv) {return arv % paisktabel.size();}

    abstract public String ylesandeKirjeldus();
    abstract public void loeSisend(String faili_tee) throws IOException;
    abstract public ArrayList<Hinnang> leiaÕigeLäbimäng();
    abstract public Hinnang hindaSammu(Samm samm);


}
