package main;

import java.util.ArrayList;

public class Paisktabel<T> {

    private int kompesamm;
    private ArrayList<ArrayList<T>> tabel;
    // topelt array, sest peab saama teha ka ahelpaiskamist

    public Paisktabel(int kompesamm) {
        this.kompesamm = kompesamm;
        this.tabel = new ArrayList<>();
    }

    public void looPaisktabel(int len) {
        for (int i = 0; i < len; i++) {
            this.tabel.add(new ArrayList<>());
        }
    }

    public void hävitaPaisktabel() {
        this.tabel = new ArrayList<>();
    }

    public boolean sisesta(int v, int k, T elem) {
        tabel.get(v).add(k, elem);
        return true;
    }

    public boolean eemalda(int v, int k) {
        ArrayList<T> ahel = tabel.get(v);
        if (ahel.size() > 0) {
            ahel.remove(k);
            return true;
        }
        return false;
    }

    public int leiaVabaKoht(int voti) {
        int index = voti;
        while (tabel.get(index).size() > 0) {
            index += kompesamm;
            if (index >= tabel.size()) {
                index -= tabel.size();
            }
            if (index == voti) {
                return -1;
            }
        }
        return index;
    }

    public int leiaAsukoht(T element, int voti) {
        int index = voti;
        while (!tabel.get(index).contains(element)) {
            index += kompesamm;
            if (index >= tabel.size()) {
                index -= tabel.size();
            }
            if (index == voti || tabel.get(index) == null) {
                return -1;
            }
        }
        return index;
    }

    public int size() {
        return tabel.size();
    }

    @Override
    public String toString() {
        String str = " ";

        for (int i = 0; i < tabel.size(); i++) {

            str += i + ": " + tabel.get(i).toString() + "\n";
        }
        return str;
    }

    public T get(int v, int k) {
        ArrayList<T> jada = tabel.get(v);
        if (jada.size() == 0) return null;
        return jada.get(k);
    }
    public ArrayList<T> get(int v) {
        return tabel.get(v);
    }


}
