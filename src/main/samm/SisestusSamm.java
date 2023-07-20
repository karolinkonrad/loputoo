package main.samm;

import main.Läbimäng;

import java.util.Objects;

public class SisestusSamm<T> implements Samm {
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
    public boolean astu(Läbimäng läbimäng) {
        if (läbimäng.getAbijärjend().size() <= index || räsi >= läbimäng.getPaisktabel().size() || koht > läbimäng.getPaisktabel().get(räsi).size())
            return false;

        element = (T) läbimäng.getAbijärjend().get(index);
        läbimäng.getPaisktabel().sisesta(räsi, koht, element);
        läbimäng.getAbijärjend().remove(index);

        return true;
    }

    @Override
    public boolean tagasi(Läbimäng läbimäng) {
        läbimäng.getPaisktabel().eemalda(räsi, koht);
        läbimäng.getAbijärjend().add(index, element);
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
