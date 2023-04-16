public abstract class Samm {
    Paisktabel paisktabel;

    public Samm(Paisktabel paisktabel) {
        this.paisktabel = paisktabel;
    }

    abstract public void execute();
    abstract public void undo();
}

