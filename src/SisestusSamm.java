public class SisestusSamm extends Samm {
    private int index;
    private int arv;
    private Integer eelmineArv;

    public SisestusSamm(Paisktabel paisktabel, int index, int arv) {
        super(paisktabel);
        this.index = index;
        this.arv = arv;
    }

    @Override
    public void execute() {
        this.eelmineArv = paisktabel.getPaisktabel()[index];
        paisktabel.sisesta(index, arv);
    }

    @Override
    public void undo() {
        paisktabel.sisesta(index, eelmineArv);
    }
}
