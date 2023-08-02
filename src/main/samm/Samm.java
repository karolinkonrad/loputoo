package main.samm;

import main.Läbimäng;

public interface Samm {
    /**
     * Läbimängus sammu astumine.
     * @param läbimäng Antud läbimäng
     * @return Kas operatsioon õnnestu?
     */
    boolean astu(Läbimäng läbimäng);

    /**
     * Läbimängus sammu tagasivõtmine.
     * @param läbimäng Antud läbimäng.
     * @return Kas operatsioon õnnestu?
     */
    boolean tagasi(Läbimäng läbimäng);

    boolean equals(Object o);
    String toString();
}
