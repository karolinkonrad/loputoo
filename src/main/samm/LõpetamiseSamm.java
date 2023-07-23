package main.samm;

import main.Läbimäng;

public class LõpetamiseSamm implements Samm {
    // kuidagi seotud hindamisega

    @Override
    public boolean astu(Läbimäng läbimäng) {
        läbimäng.lõpeta();
        return true;
    }

    @Override
    public boolean tagasi(Läbimäng läbimäng) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public String toString() {
        return "algoritm lõpetab";
    }

}
