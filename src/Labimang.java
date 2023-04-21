import java.util.ArrayList;
import java.util.Stack;

public class Labimang {
    private ArrayList<Paisktabel> paisktabelid;
    private ArrayList<Integer> sisend;
    private Ylesanne ylesanne;
    private Stack<Samm> tudengiSammud;

    private Stack<Integer> oigedJadad;
    private int oigeJada;

    public Labimang(Ylesanne ylesanne) {
        this.paisktabelid = new ArrayList<>();
        this.sisend = ylesanne.getSisend();
        this.ylesanne = ylesanne;
        this.tudengiSammud = new Stack<>();

        this.oigedJadad = new Stack<>();
        this.oigeJada = 0;
    }


    public void astu(Samm samm) {
        Samm oigeSamm = ylesanne.arvutaJargmineSamm(this);

        if (!oigeSamm.equals(samm)) {
            System.out.println("Vale!");
            System.out.println(oigeJada);
            oigedJadad.push(oigeJada);
            oigeJada = -1;

        }
        oigeJada++;
        tudengiSammud.push(samm);
        samm.astu();

    }
    public void tagasi() {
        Samm samm = tudengiSammud.pop();
        samm.tagasi();
        if (oigeJada == 0)
            oigeJada = oigedJadad.pop();
        else
            oigeJada--;
    }
    public ArrayList<Paisktabel> getPaisktabelid() {
        return paisktabelid;
    }

    public ArrayList<Integer> getSisend() {
        return sisend;
    }

    public float getHinne() {
        oigedJadad.push(oigeJada);
        int parimJada = 0;

        while (!oigedJadad.isEmpty()) {
            int jada = oigedJadad.pop();
            if (jada > parimJada) parimJada = jada;
        }
        System.out.println(parimJada + "/" + ylesanne.maxPunktid());
        return (float) parimJada / (float) ylesanne.maxPunktid();
    }

}

