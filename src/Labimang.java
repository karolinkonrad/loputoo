import java.util.Stack;

public class Labimang {
    private int[] sisend;
    private Paisktabel paisktabel;

    private Stack<Samm> sammud = new Stack<>();

    public Labimang(int[] sisend) {
        this.sisend = sisend;
    }

    public void hinda() {
        //TODO
    }

    public void make_tabel(int len) {
        this.paisktabel = new Paisktabel(len);

    }

    public void sisesta(int i, String x) {

        Samm sisesstusSamm = new SisestusSamm(paisktabel, i, x);
        executeSamm(sisesstusSamm);

    }
    public void eemalda(int i) {
        Samm eemaldusSamm = new EemaldusSamm(paisktabel, i);
        executeSamm(eemaldusSamm);
    }

    private void executeSamm(Samm samm) {
        samm.execute();
        sammud.push(samm);
    }

    @Override
    public String toString() {
        return paisktabel.toString();
    }
}
