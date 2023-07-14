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

    public Paisktabel(int kompesamm, int len) {
        this.kompesamm = kompesamm;
        this.tabel = new ArrayList<>();
        looPaisktabel(len);
    }

    public boolean sisesta(int r, int k, T elem) {
        tabel.get(r).add(k, elem);
        return true;
    }

    public boolean eemalda(int r, int k) {
        ArrayList<T> ahel = tabel.get(r);
        if (ahel.size() > k) {
            ahel.remove(k);
            return true;
        }
        return false;
    }

    public int leiaVabaKoht(int räsi) {
        int index = räsi;
        while (tabel.get(index).size() > 0) {
            index += kompesamm;
            if (index >= tabel.size()) {
                index -= tabel.size();
            }
            if (index == räsi) {
                return -1;
            }
        }
        return index;
    }

    public int leiaAsukoht(T element, int räsi) {
        int index = räsi;
        while (!tabel.get(index).contains(element)) {
            index += kompesamm;
            if (index >= tabel.size()) {
                index -= tabel.size();
            }
            if (index == räsi || tabel.get(index) == null) {
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
        String str = "";

        for (int i = 0; i < tabel.size(); i++) {

            str += i + ":" + tabel.get(i).toString() + " ";
        }
        return str;
    }

    public T get(int r, int k) {
        ArrayList<T> jada = tabel.get(r);
        if (jada.size() == 0) return null;
        return jada.get(k);
    }
    public ArrayList<T> get(int r) {
        return tabel.get(r);
    }


}
