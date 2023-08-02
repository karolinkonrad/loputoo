package main.samm;

import main.Läbimäng;

import java.util.Objects;

public class SisestamiseSamm<T> implements Samm {
    private final int indeks; //indeks abimassiivis
    private final int räsi; //võti paisktabelis
    private final int koht; //koht ahelas
    private T element;

    public SisestamiseSamm(int indeks, int räsi, int koht) {
        super();
        this.indeks = indeks;
        this.räsi = räsi;
        this.koht = koht;
    }

    @Override
    public boolean astu(Läbimäng läbimäng) {
        if (läbimäng.getAbijärjend().size() <= indeks || räsi >= läbimäng.getPaisktabel().size() || koht > läbimäng.getPaisktabel().get(räsi).size())
            return false;

        element = (T) läbimäng.getAbijärjend().get(indeks);
        läbimäng.getPaisktabel().sisesta(räsi, koht, element);
        läbimäng.getAbijärjend().remove(indeks);

        return true;
    }

    @Override
    public boolean tagasi(Läbimäng läbimäng) {
        läbimäng.getPaisktabel().eemalda(räsi, koht);
        läbimäng.getAbijärjend().add(indeks, element);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SisestamiseSamm<T> that = (SisestamiseSamm<T>) o;
        return indeks == that.indeks && räsi == that.räsi && koht == that.koht;
    }

    @Override
    public String toString() {
        return "Sisesta: m[" + indeks + "] -> p[" + räsi + "][" + koht + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(indeks, räsi, koht);
    }
}
