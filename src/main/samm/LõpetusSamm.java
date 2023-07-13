package main.samm;

import main.ylesanne.Ylesanne;

public class LõpetusSamm extends Samm {
    // kuidagi seotud hindamisega

    @Override
    public boolean astu(Ylesanne ylesanne) {
        ylesanne.lõpeta();
        return true;
    }

    @Override
    public boolean tagasi(Ylesanne ylesanne) {
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
