package main;

import main.samm.Samm;
import main.ylesanne.Ylesanne;

import java.util.ArrayList;
import java.util.Stack;

public class Läbimäng<T> {

    private Stack<Hinnang> läbimäng;

    private Ylesanne<T> ylesanne;
    private Paisktabel<T> paisktabel;
    private ArrayList<T> abijärjend;
    private ArrayList<Hinnang> õigeLäbimäng;

    private Logija logija;
    private Hindaja hindaja;

    private float punktid;


    public void setYlesanne(Ylesanne ylesanne) {
        this.läbimäng = new Stack<>();

        this.ylesanne = ylesanne;
        this.paisktabel = ylesanne.getPaisktabel();
        this.abijärjend = ylesanne.getAbijärjend();
        this.õigeLäbimäng = ylesanne.leiaÕigeLäbimäng();

        this.logija = new Logija();
        logija.logi(ylesanne.ylesandeKirjeldus());

    }

    public void setHindaja(Hindaja hindaja) {
        this.hindaja = hindaja;
    }
    public void setPaisktabeliParameetrid(float minElem, float maxElem, int elementideArv) { // m a b
        ylesanne.paisktabeliParameetrid(minElem, maxElem, elementideArv);
    }
    public Paisktabel<T> getPaisktabel() {
        return paisktabel;
    }
    public ArrayList<T> getAbijärjend() {
        return abijärjend;
    }

    public float getPunktid() {
        return punktid;
    }

    public boolean astu(Samm samm) {
        Hinnang hinnang = ylesanne.hindaSammu(samm, abijärjend, paisktabel);
        läbimäng.push(hinnang);

        logija.logi(abijärjend.toString() + "\n"
                + paisktabel.toString() + "\n"
                + "---------------------------------------------------------\n"
                + hinnang.toString());

        if (samm.astu(this)) {
            ylesanne.astu(this, hinnang);

            return true;
        }
        logija.logi("   VIGANE SAMM");
        läbimäng.pop();
        return false;
    }

    public boolean tagasi() {

        if (läbimäng.isEmpty()) return true;

        Hinnang hinnang = läbimäng.pop();
        if (hinnang.tudengiSamm.tagasi(this)) {
            ylesanne.tagasi(this, hinnang);
            logija.logi(abijärjend.toString() + "\n"
                    + paisktabel.toString() + "---------------------------------------------------------\n"
                    + "\nTAGASI:\n"
                    + hinnang);
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

    public String ylesandeKirjeldus() {
        return ylesanne.ylesandeKirjeldus();
    }
}
