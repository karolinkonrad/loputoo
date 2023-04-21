import java.util.Objects;

public class PaisktabeliLoomisSamm extends Samm {
    int paisktabeliPikkus;

    public PaisktabeliLoomisSamm(Labimang labimang, int paisktabeliPikkus) {
        super(labimang);
        this.paisktabeliPikkus = paisktabeliPikkus;
    }

    @Override
    public void astu() {
        labimang.getPaisktabelid().add(0, new Paisktabel(paisktabeliPikkus));
    }

    @Override
    public void tagasi() {
        labimang.getPaisktabelid().remove(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaisktabeliLoomisSamm that = (PaisktabeliLoomisSamm) o;
        return paisktabeliPikkus == that.paisktabeliPikkus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paisktabeliPikkus);
    }
}
