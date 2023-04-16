import java.util.ArrayList;
import java.util.Stack;

public class Labimang {
    private Sisend sisend;
    private Paisktabel paisktabel;
    private Stack<Samm> sammud;
    private ArrayList<Samm> undod;

    public Labimang(ArrayList<Integer> sisend) {
        this.sisend = new Sisend(sisend);
        this.paisktabel = new Paisktabel();
        this.sammud = new Stack<>();
        this.undod = new ArrayList<>();
    }


    public void astu(Samm samm) {
        //oigedSammud.push(getOigeSamm());
        sammud.push(samm);
        samm.execute();

    }

    public Sisend getSisend() {
        return sisend;
    }

    public Paisktabel getPaisktabel() {
        return paisktabel;
    }

    public void undo() {
        Samm samm = sammud.pop();
        samm.undo();
        undod.add(samm);

    }
}

