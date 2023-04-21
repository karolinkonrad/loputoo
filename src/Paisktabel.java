import java.util.ArrayList;

public class Paisktabel {
    private ArrayList<ArrayList<Integer>> paisktabel;
    // topelt array, sest peab saama teha ka ahelpaiskamist

    public Paisktabel(int len) {
        this.paisktabel = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            this.paisktabel.add(new ArrayList<>());
        }
    }

    public void sisesta(int i, Integer x) {
        paisktabel.get(i).add(x);
    }

    public void eemalda(int i, int x) {
        paisktabel.get(i).remove((Integer) x);
    }

    public int leiaVabaKoht(int arv) {
        int voti = arv % paisktabel.size();
        int index = voti;
        while (paisktabel.get(index).size() > 0) {
            index++;
            if (index == paisktabel.size()) {
                index = 0;
            }
            if (index == voti) {
                return -1;
            }
        }
        return index;
    }

    public int leiaArvuIndex(Integer eemaldatav) {
        int key = eemaldatav % paisktabel.size();
        int index = key;
        while (!paisktabel.get(index).contains(eemaldatav)) {
            index++;
            if (index == paisktabel.size()) {
                index = 0;
            }
            if (index == key || paisktabel.get(index) == null) {
                return -1;
            }
        }
        return index;
    }



    @Override
    public String toString() {
        String str = " ";

        for (int i = 0; i < paisktabel.size(); i++) {

            str +=  i + ": " + paisktabel.get(i).toString() + "\n";
        }
        return str;
    }

}
