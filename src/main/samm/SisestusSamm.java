package main.samm;

import main.ylesanne.Ylesanne;

import java.util.Objects;

public class SisestusSamm<T> extends Samm {
    private int index; //indeks abimassiivis
    private int voti; //võti paisktabelis
    private int koht; //koht ahelas
    private T element;

    public SisestusSamm(int index, int voti, int koht) {
        super();
        this.index = index;
        this.voti = voti;
        this.koht = koht;
    }

    @Override
    public void astu(Ylesanne ylesanne) {
        element = (T) ylesanne.getAbiMassiiv().get(index);
        ylesanne.getPaisktabel().sisesta(voti, koht, element);
        ylesanne.getAbiMassiiv().remove(index);
    }

    @Override
    public boolean tagasi(Ylesanne ylesanne) {
        ylesanne.getPaisktabel().eemalda(voti, koht);
        ylesanne.getAbiMassiiv().add(index, element);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SisestusSamm that = (SisestusSamm) o;
        return index == that.index && voti == that.voti && koht == that.koht && element == that.element;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, voti, koht, element);
    }
}
