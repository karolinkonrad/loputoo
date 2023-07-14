package main.samm;


import main.Läbimäng;

public abstract class Samm{
    abstract public boolean astu(Läbimäng läbimäng);

    abstract public boolean tagasi(Läbimäng läbimäng);

    public abstract boolean equals(Object o);

    public abstract String toString();
}

