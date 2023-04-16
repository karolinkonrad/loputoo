public class EemaldusSamm extends Samm {
    int index;
    int eemaldatudArv;

    public EemaldusSamm(Paisktabel paisktabel, int index) {
        super(paisktabel);
        this.index = index;
    }

    @Override
    public void execute() {
        eemaldatudArv= paisktabel.getPaisktabel()[index];
        paisktabel.eemalda(index);
    }

    @Override
    public void undo() {
        paisktabel.sisesta(index, eemaldatudArv);
    }
}
