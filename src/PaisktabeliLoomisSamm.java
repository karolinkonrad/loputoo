public class PaisktabeliLoomisSamm extends Samm {
    int paisktabeli_pikkus;

    public PaisktabeliLoomisSamm(Paisktabel paisktabel, int len) {
        super(paisktabel);
        paisktabeli_pikkus = len;
    }

    @Override
    public void execute() {
        paisktabel.makePaisktabel(paisktabeli_pikkus);
    }

    @Override
    public void undo() {

    }
}
