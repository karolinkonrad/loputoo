package main.samm;

import main.ylesanne.Ylesanne;

import java.util.Objects;

public class PaisktabeliLoomisSamm extends Samm {
    private final float minElement;
    private final float maxElement;
    private final int paisktabeliPikkus;

    public PaisktabeliLoomisSamm(int paisktabeliPikkus) {
        super();
        this.minElement = 0;
        this.maxElement = 0;
        this.paisktabeliPikkus = paisktabeliPikkus;
    }

    public PaisktabeliLoomisSamm(float minElement, float maxElement, int paisktabeliPikkus) {
        super();
        this.minElement = minElement;
        this.maxElement = maxElement;
        this.paisktabeliPikkus = paisktabeliPikkus;
    }

    @Override
    public void astu(Ylesanne ylesanne) {
        ylesanne.paisktabeliParameetrid(minElement, maxElement, paisktabeliPikkus);
        ylesanne.getPaisktabel().looPaisktabel(paisktabeliPikkus);
    }

    @Override
    public boolean tagasi(Ylesanne ylesanne) {
        ylesanne.getPaisktabel().hävitaPaisktabel();
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaisktabeliLoomisSamm that = (PaisktabeliLoomisSamm) o;
        return paisktabeliPikkus == that.paisktabeliPikkus;
    }

    @Override
    public String toString() {
        return "Uus paisktabel: minElement=" + minElement + "; maxElement=" + maxElement + "; pikkus=" + paisktabeliPikkus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paisktabeliPikkus);
    }
}
