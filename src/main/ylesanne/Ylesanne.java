package main.ylesanne;

import main.Hinnang;
import main.Läbimäng;
import main.Paisktabel;
import main.samm.Samm;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Ylesanne<T> {

    /**
     * Arvu räsi arvutamine jääkpaiskamisega.
     * @param arv Antud arv.
     * @param paisktabel Antud paisktabel.
     * @return Arvu räsi
     */
    public int paiskfunktsioon(int arv, Paisktabel<T> paisktabel) {return arv % paisktabel.size();}

    /**
     * Failist andmete lugemine.
     * @param failiTee Antud faili tee
     * @throws IOException
     */
    abstract public void loeSisend(String failiTee) throws IOException;

    /**
     * @return Algne paisktabel.
     */
    abstract public Paisktabel<T> getPaisktabel();

    /**
     * @return Algne abijärjend
     */
    abstract public ArrayList<T> getAbijärjend();

    abstract public void setPaisktabeliParameetrid(float minElem, float maxElem, int elementideArv);

    /**
     * Õige läbimängu läbi tegemine.
     * @return Läbimängu kujutav hinnangute jada.
     */
    abstract public ArrayList<Hinnang> leiaÕigeLäbimäng();

    /**
     * Ülesandekirjelduse koostamine.
     * @return Ülesande kirjeldus.
     */
    abstract public String ylesandeKirjeldus();

    /**
     * Ülesande seisus muudatuste tegemine.
     * @param läbimäng Jooksev läbimäng.
     * @param hinnang Viimase sammu hinnang.
     */
    abstract public void astu(Läbimäng<T> läbimäng, Hinnang hinnang);

    /**
     * Ülesande seisu tagasi võtmine.
     * @param läbimäng Jooksev läbimäng.
     * @param hinnang Viimase sammu hinnang.
     */
    abstract public void tagasi(Läbimäng<T> läbimäng, Hinnang hinnang);

    /**
     * Tudengi tehtud sammule õige sammu leidmine.
     * @param samm Tudengi tehtud samm.
     * @param abijärjend Jooksva läbimängu abijärjendi seis.
     * @param paisktabel Jooksva läbimängu paisktabeli seis.
     * @return Hinnang tehtud sammule.
     */
    abstract public Hinnang hindaSammu(Samm samm, ArrayList<T> abijärjend, Paisktabel<T> paisktabel);

}
