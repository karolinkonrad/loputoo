package main.ylesanne;

import main.Hindaja;
import main.Logija;
import main.Paisktabel;
import main.samm.Samm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public abstract class Ylesanne<T> {
    // operatsiooni liik
    static final int RASKEOP = 1;
    static final int LISAMINE = 2;
    static final int TABELIOP = 3;
    static final int LÕPP = 4;
    static final int EEMALDAMINE = 5;
    static final int KUSTUTAMINE = 6;

    private Stack<Samm> sammud;
    private Stack<Integer> läbimäng;
    private Logija logija;
    private Hindaja hindaja;

    private Ylesanne ylesanne;
    protected Paisktabel<T> paisktabel;
    protected ArrayList<T> abiMassiiv;
    private ArrayList<Integer> õigeLäbimäng;

    private float punktid;

    public Ylesanne(Hindaja hindaja) throws IOException {
        this.sammud = new Stack<>();
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

    public void setÕigeLäbimäng(ArrayList<Integer> õigeLäbimäng) {
        this.õigeLäbimäng = õigeLäbimäng;
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
        läbimäng.push(hinnang);
        sammud.push(samm);

        logija.logi("---------------------------------------------------------\n"
                + abiMassiiv.toString() + "\n"
                + paisktabel.toString() + "\n>"
                + samm.toString()+ "\n" +
                hinnang );

        samm.astu(this);
    }

    public boolean tagasi() {

        if (sammud.isEmpty()) return true;

        Samm samm = sammud.pop();
        if (samm.tagasi(this)) {
            int hinnang = läbimäng.pop();
            ylesanne.tagasiJärg(hinnang);
            logija.logi("---------------------------------------------------------\n"
                    + abiMassiiv.toString() + "\n"
                    + paisktabel.toString() + "\ntagasi");
        }
        else
            sammud.push(samm);

        if (sammud.isEmpty()) return true;
        return false;
    }



    protected void astuJärg(int hinnang) {}
    protected void tagasiJärg(int hinnang) {}

    public void lõpeta() {
        punktid = hindaja.arvutaHinne(läbimäng, õigeLäbimäng);
        logija.logi("###########################\nHinne: " + punktid + "%\n"
        + õigeLäbimäng.toString());
    }

    public float getPunktid() {
        return punktid;
    }
    public int paiskfunktsioon(int arv) {return arv % paisktabel.size();}

    abstract public String ylesandeKirjeldus();
    abstract public void loeSisend(String faili_tee) throws IOException;
    abstract public ArrayList<Integer> leiaÕigeLäbimäng();
    abstract public int hindaSammu(Samm samm);


}
