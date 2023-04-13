public class SisestusSamm implements Samm {
    private Paisktabel paisktabel;
    private int index;
    private String el;


    public SisestusSamm(Paisktabel paisktabel, int index, String el) {
        this.paisktabel = paisktabel;
        this.index = index;
        this.el = el;
    }

    @Override
    public void execute() {
        paisktabel.sisesta(index, el);
    }
}
