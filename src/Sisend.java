import java.util.ArrayList;

public class Sisend {
    private ArrayList<Integer> sisend;
    private Paisktabel paisktabel;
    private ArrayList<Samm> oigedSammud;

    public Sisend(ArrayList<Integer> sisend) {
        this.sisend = sisend;
        this.paisktabel = new Paisktabel();
        this.oigedSammud = new ArrayList<>();
        arvutaSammud();
    }

    public Sisend(ArrayList<Integer> sisend, Paisktabel paisktabel) {
        this.sisend = sisend;
        this.paisktabel = paisktabel;
        this.oigedSammud = new ArrayList<>();
        arvutaSammud();
    }

    private void arvutaSammud() {
        this.oigedSammud = new ArrayList<>();
        if (paisktabel.getPaisktabel() == null) {
            astu(new PaisktabeliLoomisSamm(this.paisktabel, this.sisend.size()));

        }
        for (int arv : sisend) {
            astu(new SisestusSamm(this.paisktabel, paisktabel.leiaVabaKoht(arv), arv));
        }
    }


    public void astu(Samm samm) {
        //oigedSammud.push(getOigeSamm());
        oigedSammud.add(samm);
        samm.execute();

    }
    public int size() {
        return sisend.size();
    }

    @Override
    public String toString() {
        return "Sisesta paisktabelisse " + sisend.toString();
    }
}
