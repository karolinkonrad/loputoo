package main;

import main.samm.Samm;

public class Hinnang {
    public final Samm õigeSamm;
    public final Hindaja.Olek olek;
    public final Samm tudengiSamm;
    public final boolean õige;

    public Hinnang(Samm õigeSamm, Hindaja.Olek olek, Samm tudengiSamm, boolean õige) {
        this.õigeSamm = õigeSamm;
        this.olek = olek;
        this.tudengiSamm = tudengiSamm;
        this.õige = õige;
    }

    @Override
    public String toString() {
        if (õige) {
            return "> " + tudengiSamm.toString() + "\n"
                    + olek;
        }
        return "*VIGA*\n> " + tudengiSamm.toString() + "\n"
                + "õige -> " + õigeSamm.toString() + "\n"
                + olek;
    }
}
