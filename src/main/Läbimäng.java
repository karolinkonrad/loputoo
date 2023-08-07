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

    /**
     * Ülesande määramine ja algväärtuste määramine.
     * @param ylesanne Antud ülesanne.
     */
    public void setYlesanne(Ylesanne<T> ylesanne) {
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
        ylesanne.setPaisktabeliParameetrid(minElem, maxElem, elementideArv);
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

    /**
     * Sammu ehk käsu kontrollimine, täitmine ja salvestamine.
     * @param samm Antud käsk.
     * @return Kas käsk täideti edukalt?
     */
    public boolean astu(Samm samm) {
        Hinnang hinnang = ylesanne.hindaSammu(samm, abijärjend, paisktabel);
        läbimäng.push(hinnang);

        logija.logi(abijärjend.toString() + "\n"
                + paisktabel.toString()
                + "\n---------------------------------------------------------\n"
                + hinnang.toString());

        if (samm.astu(this)) {
            ylesanne.astu(this, hinnang);

            return true;
        }
        logija.logi("   VIGANE KÄSK");
        läbimäng.pop();
        return false;
    }

    /**
     * Sammu ehk käsu tagasivõtmine.
     * @return Kas sammude jada oli tühi?
     */
    public boolean tagasi() {

        if (läbimäng.isEmpty()) return true;

        Hinnang hinnang = läbimäng.pop();
        if (hinnang.tudengiSamm.tagasi(this)) {
            ylesanne.tagasi(this, hinnang);
            logija.logi(abijärjend.toString() + "\n"
                    + paisktabel.toString() + "\n---------------------------------------------------------\n"
                    + "\nTAGASI:\n"
                    + hinnang);
        }
        else
            läbimäng.push(hinnang);

        return false;
    }

    /**
     * Läbimängu lõpetamine ja hinde arvutamine.
     */
    public void lõpeta() {
        punktid = hindaja.arvutaHinne(läbimäng, õigeLäbimäng);
        logija.logi("###########################\nHinne: " + punktid + "%\n");
    }

    /**
     * Ülesande kirjelduse edastamine.
     * @return
     */
    public String ylesandeKirjeldus() {
        return ylesanne.ylesandeKirjeldus();
    }
}
