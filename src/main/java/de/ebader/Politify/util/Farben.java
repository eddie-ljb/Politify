package de.ebader.Politify.util;

import java.awt.Color;

public enum Farben {

    WEISS(new Color(252, 252, 252)), //
    LAVENDEL(new Color(142, 130, 187)), //
    PASTELL_LILA(new Color(114, 101, 177)), //
    HELLBRAUN(new Color(102, 74, 41)), //
    DUNKELROT(new Color(102, 41, 41)), //
    MITTELBLAU(new Color(37, 37, 208)), //
    SEHR_HELLES_BLAU(new Color(204, 229, 255)), //
    ORANGE(new Color(255, 128, 0)), //
    DP_ROT(new Color(153, 0, 0)), //
    HELLERES_ORANGE(new Color(255, 155, 40)), //
    MINZ_TUERKIS(new Color(10, 150, 143)), //
    DEZENT_BLAU(new Color(102, 102, 255)), //
    TIEF_LILA(new Color(153, 0, 153)), //
    PINKE_HAUTFARBE(new Color(239, 151, 227)), //
    MATT_LILA(new Color(160, 10, 160)); //

    private Color farbe;

    private Farben(Color farbe) {
        this.farbe = farbe;
    }

    public Color getFarbe() {
        return this.farbe;
    }

}
