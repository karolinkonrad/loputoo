package main.samm;

import main.ylesanne.Ylesanne;

import java.util.Objects;

public class SisestusSamm<T> extends Samm {
    private final int index; //indeks abimassiivis
    private final int räsi; //võti paisktabelis
    private final int koht; //koht ahelas
    private T element;

    public SisestusSamm(int index, int räsi, int koht) {
        super();
        this.index = index;
        this.räsi = räsi;
        this.koht = koht;
    }

    @Override
    public boolean astu(Ylesanne ylesanne) {
        element = (T) ylesanne.getAbiMassiiv().get(index);
        if (element == null || räsi >= ylesanne.getPaisktabel().size() || koht > ylesanne.getPaisktabel().get(räsi).size())
            return false;

        ylesanne.getPaisktabel().sisesta(räsi, koht, element);
        ylesanne.getAbiMassiiv().remove(index);

        return true;
    }

    @Override
    public boolean tagasi(Ylesanne ylesanne) {
        ylesanne.getPaisktabel().eemalda(räsi, koht);
        ylesanne.getAbiMassiiv().add(index, element);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SisestusSamm that = (SisestusSamm) o;
        return index == that.index && räsi == that.räsi && koht == that.koht;
    }

    @Override
    public String toString() {
        return "Sisesta: m[" + index + "] -> p[" + räsi + "][" + koht + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, räsi, koht);
    }
}
