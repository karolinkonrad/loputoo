package main.samm;

import main.Labimang;

import java.util.Objects;

public class EemaldusSamm extends Samm {
    private int koht;
    private int arv;

    public EemaldusSamm(Labimang labimang, int koht, int arv) {
        super(labimang);
        this.koht = koht;
        this.arv = arv;
    }

    @Override
    public void astu() {
        labimang.getPaisktabel().eemalda(koht, arv);
        labimang.getSisend().add(arv);
    }

    @Override
    public void tagasi() {
        labimang.getPaisktabel().sisesta(koht, arv);
        labimang.getSisend().remove((Integer) arv);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EemaldusSamm that = (EemaldusSamm) o;
        return koht == that.koht && arv == that.arv;
    }

    @Override
    public int hashCode() {
        return Objects.hash(koht, arv);
    }
}
