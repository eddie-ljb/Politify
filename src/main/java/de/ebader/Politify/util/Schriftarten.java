package de.ebader.Politify.util;

import java.awt.Font;

public enum Schriftarten {


    SEGOE_BLACK_PLAIN_13(new Font("Segoe UI Black", Font.PLAIN, 13)), //
    SEGOE_BLACK_PLAIN_12(new Font("Segoe UI Black", Font.PLAIN, 12)), //
    SEGOE_BLACK_BOLD_14(new Font("Segoe UI Black", Font.BOLD, 14)), //
    SEGOE_BLACK_PLAIN_16(new Font("Segoe UI Black", Font.PLAIN, 16)), //
    SEGOE_BLACK_PLAIN_24(new Font("Segoe UI Black", Font.PLAIN, 24)), //
    SEGOE_BLACK_BOLD_11(new Font("Segoe UI Black", Font.BOLD, 11)), //
    SEGOE_BLACK_PLAIN_11(new Font("Segoe UI Black", Font.PLAIN, 11)), //
    SEGOE_BLACK_BOLD_12(new Font("Segoe UI Black", Font.BOLD, 12)), //
    SEGOE_BLACK_PLAIN_10(new Font("Segoe UI Black", Font.PLAIN, 10)); //

    private Font schriftart;

    private Schriftarten(Font schriftart) {
        this.schriftart = schriftart;
    }

    public Font getSchriftart() {
        return schriftart;
    }

}