package main.samm;

import main.Labimang;

import java.util.Stack;

public class LopetusSamm extends Samm {
    // kuidagi seotud hindamisega


    public LopetusSamm(Labimang labimang) {
        super(labimang);
    }

    @Override
    public void astu() {
        labimang.lõpeta();
    }

    @Override
    public boolean tagasi() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

}
