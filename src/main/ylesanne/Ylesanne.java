package main.ylesanne;

import main.Hinnang;
import main.Paisktabel;
import main.samm.Samm;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Ylesanne<T> {

    public int paiskfunktsioon(int arv, Paisktabel paisktabel) {return arv % paisktabel.size();}

    abstract public void loeSisend(String faili_tee) throws IOException;
    abstract public Paisktabel<T> getPaisktabel();
    abstract public ArrayList<T> getAbijärjend();
    abstract public void paisktabeliParameetrid(float minElem, float maxElem, int elementideArv);
    abstract public ArrayList<Hinnang> leiaÕigeLäbimäng();

    abstract public String ylesandeKirjeldus();
    abstract public void astu(Hinnang hinnang);
    abstract public void tagasi(Hinnang hinnang);
    abstract public Hinnang hindaSammu(Samm samm, ArrayList<T> abimassiiv, Paisktabel<T> paisktabel);

}
