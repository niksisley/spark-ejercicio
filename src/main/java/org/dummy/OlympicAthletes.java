package org.dummy;

import java.io.Serializable;

public class OlympicAthletes implements Serializable {
    private final int olympicGame;
    private final int medallByUSA;
    private final int medallByChina;
    private final int diferencia;

    public OlympicAthletes(int olympicGame, int medallByUSA, int medallByChina, int diferencia) {
        this.olympicGame = olympicGame;
        this.medallByUSA = medallByUSA;
        this.medallByChina = medallByChina;
        this.diferencia = diferencia;
    }

    public int getolympicGame() {
        return olympicGame;
    }

    public int getmedallByUSA() {
        return medallByUSA;
    }

    public int getmedallByChina() {
        return medallByChina;
    }

    public int getdiferencia() {
        return diferencia;
    }

}
