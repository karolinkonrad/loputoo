package main.samm;

import main.Labimang;

import java.util.Objects;

public class SisestusSamm extends Samm {
    private int koht;
    private int arv;
    private int kohtSisendis;

    public SisestusSamm(Labimang labimang, int koht, int arv) {
        super(labimang);
        this.koht = koht;
        this.arv = arv;
    }

    @Override
    public void astu() {
        labimang.getPaisktabel().sisesta(koht, arv);
        kohtSisendis = labimang.getSisend().indexOf(arv);
        labimang.getSisend().remove(kohtSisendis);
    }

    @Override
    public boolean tagasi() {
        labimang.getPaisktabel().eemalda(koht, arv);
        labimang.getSisend().add(kohtSisendis, arv);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SisestusSamm that = (SisestusSamm) o;
        return koht == that.koht && arv == that.arv && kohtSisendis == that.kohtSisendis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(koht, arv, kohtSisendis);
    }
}
