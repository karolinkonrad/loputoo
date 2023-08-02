package main;

import java.util.ArrayList;

public class Paisktabel<T> {

    private final int kompesamm;
    private ArrayList<ArrayList<T>> tabel;
    // topelt array, sest peab saama teha ka ahelpaiskamist

    public Paisktabel(int kompesamm) {
        this.kompesamm = kompesamm;
        this.tabel = new ArrayList<>();
    }

    public Paisktabel(int kompesamm, int len) {
        this.kompesamm = kompesamm;
        this.tabel = new ArrayList<>();
        looPaisktabel(len);
    }

    /**
     * Initsialiseerib tabeli eraldiseisvalt konstruktorist.
     * Rakendamine peale seimest konstrukori kasutust.
     * @param len Paisktabeli pikkus.
     */
    public void looPaisktabel(int len) {
        for (int i = 0; i < len; i++) {
            this.tabel.add(new ArrayList<>());
        }
    }

    /**
     * Kustutab kõik tabeli read.
     * Rakendamine PaisktabeliLoomiseSammu tagasivõtmisel
     */
    public void hävitaPaisktabel() {
        this.tabel = new ArrayList<>();
    }

    /**
     * Elemendi sisestamine tabelisse.
     * @param r Rea indeks paisktabelis.
     * @param k Koht antud reas.
     * @param elem Sisestatav element.
     * @return Kas sisestamine õnnestus?
     */
    public boolean sisesta(int r, int k, T elem) {
        tabel.get(r).add(k, elem);
        return true;
    }

    /**
     * Elemendi eemaldamine tabelist.
     * @param r Rea indeks tabelis.
     * @param k Koht reas.
     * @return Kas eemaldamine õnnestus?
     */
    public boolean eemalda(int r, int k) {
        ArrayList<T> ahel = tabel.get(r);
        if (ahel.size() > k) {
            ahel.remove(k);
            return true;
        }
        return false;
    }

    /**
     * Elemendi koha leidmine lahtise adresseerimisega paisktabeli loogika põhimõttel.
     * @param räsi Elemendi räsi.
     * @return Esimene vaba koht antud räsist alates.
     */
    public int leiaVabaKoht(int räsi) {
        int indeks = räsi;
        while (tabel.get(indeks).size() > 0) {
            indeks += kompesamm;
            if (indeks >= tabel.size()) {
                indeks -= tabel.size();
            }
            if (indeks == räsi) {
                return -1;
            }
        }
        return indeks;
    }

    /**
     * Elemendi leidmine lahtise adresseerimisega paisktabeli loogika põhimõttel.
     * @param element Otsitav element.
     * @param räsi Elemendi räsi.
     * @return Rida paisktabelis, kus otsitav element asub.
     */
    public int leiaAsukoht(T element, int räsi) {
        int indeks = räsi;
        while (!tabel.get(indeks).contains(element)) {
            indeks += kompesamm;
            if (indeks >= tabel.size()) {
                indeks -= tabel.size();
            }
            if (indeks == räsi || tabel.get(indeks) == null) {
                return -1;
            }
        }
        return indeks;
    }

    /**
     * Paisktabeli pikkuse leidmine.
     * @return Tabeli pikkus.
     */
    public int size() {
        return tabel.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < tabel.size(); i++) {

            str.append(i).append(":").append(tabel.get(i).toString()).append(" ");
        }
        return str.toString();
    }

    /**
     * Elemendi võtmine.
     * @param r Rea number tabelis.
     * @param k Koht reas.
     * @return Element tabelist.
     */
    public T get(int r, int k) {
        ArrayList<T> jada = tabel.get(r);
        if (jada.size() == 0) return null;
        return jada.get(k);
    }

    /**
     * Rea võtmine.
     * @param r Rea number tabelis
     * @return Rida tabelist.
     */
    public ArrayList<T> get(int r) {
        return tabel.get(r);
    }


}
