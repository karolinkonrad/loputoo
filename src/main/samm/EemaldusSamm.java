package main.samm;

import main.Läbimäng;

import java.util.Objects;

public class EemaldusSamm<T> extends Samm {
    private final int index; //indeks abimassiivis
    private final int räsi; //võti paisktabelis
    private final int koht; //koht ahelas
    private T element;

    public EemaldusSamm(int index, int räsi, int koht) {
        super();
        this.index = index;
        this.räsi = räsi;
        this.koht = koht;
    }

    @Override
    public boolean astu(Läbimäng läbimäng) {
        element = (T) läbimäng.getPaisktabel().get(räsi, koht);
        if (element == null || index >= läbimäng.getAbiMassiiv().size())
            return false;

        läbimäng.getPaisktabel().eemalda(räsi, koht);
        läbimäng.getAbiMassiiv().add(index, element);

        return true;
    }

    @Override
    public boolean tagasi(Läbimäng läbimäng) {
        läbimäng.getPaisktabel().sisesta(räsi, koht, element);
        läbimäng.getAbiMassiiv().remove(index);
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
