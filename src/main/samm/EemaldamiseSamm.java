package main.samm;

import main.Läbimäng;

import java.util.Objects;

public class EemaldamiseSamm<T> implements Samm {
    private final int indeks; //indeks abimassiivis
    private final int räsi; //võti paisktabelis
    private final int koht; //koht ahelas
    private T element;

    public EemaldamiseSamm(int indeks, int räsi, int koht) {
        super();
        this.indeks = indeks;
        this.räsi = räsi;
        this.koht = koht;
    }

    @Override
    public boolean astu(Läbimäng läbimäng) {
        element = (T) läbimäng.getPaisktabel().get(räsi, koht);
        if (element == null || indeks > läbimäng.getAbijärjend().size())
            return false;

        läbimäng.getPaisktabel().eemalda(räsi, koht);
        läbimäng.getAbijärjend().add(indeks, element);

        return true;
    }

    @Override
    public boolean tagasi(Läbimäng läbimäng) {
        läbimäng.getPaisktabel().sisesta(räsi, koht, element);
        läbimäng.getAbijärjend().remove(indeks);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EemaldamiseSamm<T> that = (EemaldamiseSamm) o;
        return indeks == that.indeks && räsi == that.räsi && koht == that.koht;
    }

    @Override
    public String toString() {
        return "Eemalda: p[" + räsi + "][" + koht + "] -> m[" + indeks + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(indeks, räsi, koht);
    }
}
