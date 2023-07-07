package main;

import main.samm.LopetusSamm;
import main.samm.Samm;

public class Hinnang {
    Samm tudengiSamm;
    Samm õigeSamm;
    public int hinnang;

    public Hinnang(Samm tudengiSamm) {
        this.tudengiSamm = tudengiSamm;
        this.õigeSamm = new LopetusSamm();
        this.hinnang = 0;
    }

    @Override
    public String toString() {
        return "tudengiSamm: " + tudengiSamm +
                "\nõigeSamm: " + õigeSamm +
                "\nhinnang: " + hinnang;
    }
}
