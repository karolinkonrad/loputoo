package main;

import main.samm.Samm;

public class Hinnang {
    public final Samm õigeSamm;
    public final int liik;
    public final Samm tudengiSamm;
    public final boolean õige;

    public Hinnang(Samm õigeSamm, int liik, Samm tudengiSamm, boolean õige) {
        this.õigeSamm = õigeSamm;
        this.liik = liik;
        this.tudengiSamm = tudengiSamm;
        this.õige = õige;
    }

    @Override
    public String toString() {
        if (õige) {
            return "> " + tudengiSamm.toString() + "\n"
                    + liik;
        }
        return "_VIGA_\n> " + tudengiSamm.toString() + "\n"
                + "õige -> " + õigeSamm.toString() + "\n"
                + liik;
    }
}
