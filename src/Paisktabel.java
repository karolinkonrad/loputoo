
public class Paisktabel {
    private Integer[] paisktabel;

    public void makePaisktabel(int len) {
        this.paisktabel = new Integer[len];
    }

    public void delPaisktabel() {
        this.paisktabel = null;
    }


    public Integer[] getPaisktabel() {
        return paisktabel;
    }

    public void sisesta(int i, Integer x) {
        paisktabel[i] = x;
    }

    public void eemalda(int i) {
        paisktabel[i] = null;
    }

    public int leiaVabaKoht(int arv) {
        int index = arv % paisktabel.length;
        while (paisktabel[index] != null) {
            index++;
            if (index == paisktabel.length) {
                index = 0;
            }
            if (index == arv % paisktabel.length) {
                return -1;
            }
        }
        return index;
    }

    public int leiaArvuIndex(Integer eemaldatav) {
        int key = eemaldatav % paisktabel.length;
        int index = key;
        while (paisktabel[index] != eemaldatav) {
            index++;
            if (index == paisktabel.length) {
                index = 0;
            }
            if (index == key || paisktabel[index] == null) {
                return -1;
            }
        }
        return index;
    }



    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < paisktabel.length; i++) {
            str += " | " + i;
        }
        str += " |\n [ ";
        for (Integer el : paisktabel) {
            if (el == null) str += "_";
            else str += el;
            str += " ; ";
        }
        str += "]";
        return str;
    }

}
