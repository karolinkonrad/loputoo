import java.util.ArrayList;

public class Paisktabel {
    private String[] paisktabel;

    public Paisktabel(int len) {
        this.paisktabel = new String[len];
    }

    public void sisesta(int i, String x) {
        paisktabel[i] = x;
    }

    public void eemalda(int i) {
        paisktabel[i] = null;
    }

    @Override
    public String toString() {
        String str = "";
        str += "  0";
        for (int i = 1; i < paisktabel.length; i++) {
            str += " | " + i;
        }
        str += "\n[ ";
        for (String el : paisktabel) {
            if (el == null) str += "_";
            else str += el;
            str += " ; ";
        }
        str += "]";
        return str;
    }
}
