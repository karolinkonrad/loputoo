import java.util.Objects;

public class EemaldusSamm extends Samm {
    private int tabel;
    private int koht;
    private int arv;

    public EemaldusSamm(Labimang labimang, int tabel, int koht, int arv) {
        super(labimang);
        this.tabel = tabel;
        this.koht = koht;
        this.arv = arv;
    }

    @Override
    public void astu() {
        labimang.getPaisktabelid().get(tabel).eemalda(koht, arv);
        labimang.getSisend().add(arv);
    }

    @Override
    public void tagasi() {
        labimang.getPaisktabelid().get(tabel).sisesta(koht, arv);
        labimang.getSisend().remove((Integer) arv);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EemaldusSamm that = (EemaldusSamm) o;
        return tabel == that.tabel && koht == that.koht && arv == that.arv;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tabel, koht, arv);
    }
}
