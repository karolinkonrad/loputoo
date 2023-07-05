package main.samm;

import main.ylesanne.Ylesanne;

import java.util.Objects;

public class EemaldusSamm<T> extends Samm {
    private int index; //indeks abimassiivis
    private int räsi; //võti paisktabelis
    private int koht; //koht ahelas
    private T element;

    public EemaldusSamm(int index, int räsi, int koht) {
        super();
        this.index = index;
        this.räsi = räsi;
        this.koht = koht;
    }

    @Override
    public void astu(Ylesanne ylesanne) {
        element = (T) ylesanne.getPaisktabel().get(räsi, koht);
        ylesanne.getPaisktabel().eemalda(räsi, koht);
        ylesanne.getAbiMassiiv().add(index, element);
    }

    @Override
    public boolean tagasi(Ylesanne ylesanne) {
        ylesanne.getPaisktabel().sisesta(räsi, koht, element);
        ylesanne.getAbiMassiiv().remove(index);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EemaldusSamm that = (EemaldusSamm) o;
        return index == that.index && räsi == that.räsi && koht == that.koht;
    }

    @Override
    public String toString() {
        return "Eemalda: p[" + räsi + "][" + koht + "] -> m[" + index + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, räsi, koht);
    }
}
