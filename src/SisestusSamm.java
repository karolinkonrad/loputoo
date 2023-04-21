import java.util.Objects;

public class SisestusSamm extends Samm {
    private int tabel;
    private int koht;
    private int arv;
    private int kohtSisendis;

    public SisestusSamm(Labimang labimang, int tabel, int koht, int arv) {
        super(labimang);
        this.tabel = tabel;
        this.koht = koht;
        this.arv = arv;
    }

    @Override
    public void astu() {
        labimang.getPaisktabelid().get(tabel).sisesta(koht, arv);
        kohtSisendis = labimang.getSisend().indexOf(arv);
        labimang.getSisend().remove(kohtSisendis);
    }

    @Override
    public void tagasi() {
        labimang.getPaisktabelid().get(tabel).eemalda(koht, arv);
        labimang.getSisend().add(kohtSisendis, arv);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SisestusSamm that = (SisestusSamm) o;
        return tabel == that.tabel && koht == that.koht && arv == that.arv && kohtSisendis == that.kohtSisendis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tabel, koht, arv, kohtSisendis);
    }
}
