package main.samm;

import main.Läbimäng;

public interface Samm {
    boolean astu(Läbimäng läbimäng);
    boolean tagasi(Läbimäng läbimäng);
    boolean equals(Object o);
    String toString();
}
