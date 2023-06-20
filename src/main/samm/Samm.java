package main.samm;

import main.Labimang;

public abstract class Samm{
    Labimang labimang;

    public Samm(Labimang labimang) {
        this.labimang = labimang;
    }

    abstract public void astu();
    abstract public boolean tagasi();

    public abstract boolean equals(Object o);
}

