public class EemaldusSamm implements Samm {
    Paisktabel paisktabel;
    int index;
    public EemaldusSamm(Paisktabel paisktabel, int index) {
        this.paisktabel = paisktabel;
        this.index = index;
    }

    @Override
    public void execute() {
        paisktabel.eemalda(index);
    }
}
