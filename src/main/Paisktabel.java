package main;

import java.util.ArrayList;

public class Paisktabel {
    private ArrayList<ArrayList<Integer>> paisktabel;
    // topelt array, sest peab saama teha ka ahelpaiskamist

    public Paisktabel() {
        this.paisktabel = new ArrayList<>();

    }
    public void looPaisktabel(int len) {
        for (int i = 0; i < len; i++) {
            this.paisktabel.add(new ArrayList<>());
        }
    }

    public void hävitaPaisktabel() {
        this.paisktabel = new ArrayList<>();
    }

    public boolean sisesta(int i, Integer x) {
        paisktabel.get(i).add(x);
        return true;
    }

    public boolean eemalda(int i, int x) {
        ArrayList<Integer> ahel = paisktabel.get(i);
        if (ahel.contains(x)) {
            ahel.remove((Integer) x);
            return true;
        }
        return false;
    }

    public int leiaVabaKoht(int voti) {
        int index = voti;
        while (paisktabel.get(index).size() > 0) {
            index++;
            if (index == paisktabel.size()) {
                index = 0;
            }
            if (index == voti) {
                return -1;
            }
        }
        return index;
    }

    public int leiaArvuIndex(Integer eemaldatav, int voti) {
        int index = voti;
        while (!paisktabel.get(index).contains(eemaldatav)) {
            index++;
            if (index == paisktabel.size()) {
                index = 0;
            }
            if (index == voti || paisktabel.get(index) == null) {
                return -1;
            }
        }
        return index;
    }

    public int size() {
        return paisktabel.size();
    }

    @Override
    public String toString() {
        String str = " ";

        for (int i = 0; i < paisktabel.size(); i++) {

            str += i + ": " + paisktabel.get(i).toString() + "\n";
        }
        return str;
    }

}
