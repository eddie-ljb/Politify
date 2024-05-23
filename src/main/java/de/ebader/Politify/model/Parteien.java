package de.ebader.Politify.model;

import de.ebader.Politify.controller.dao.JsonParserAPI;

public enum Parteien {


    SONSTIGE(0), CDUCSU(1), SPD(2), FDP(3), GRUENE(4), LINKE(5), PIRATEN(6), AFD(7), FW(8), NPD(9), SSW(
        10), BAYERNPARTEI(11), OEDP(12), DIEPARTEI(
            13), BVBFW(14), TIERSCHUTZPARTEI(15), BIW(16), VOLT(18), BUNT_SAAR(21), BFTH(22), CDU(101), CSU(102);

    private String name;
    private String shortcut;
    private int id;
    private JsonParserAPI jsonParser = new JsonParserAPI();
    private static JsonParserAPI jsonParserStatic = new JsonParserAPI();

    private Parteien(int id) {
        this.id = id;
        this.name = jsonParser.parseParteiShortcut(id);
        this.shortcut = jsonParser.parseParlamentName(id);
    }

    public int getParteiID() {
        return id;
    }

    public String getParteiShortcut() {
        return jsonParser.parseParteiShortcut(id);

    }

    public String getParteiName() {
        return jsonParser.parseParteiName(id);
    }

    public static String getParteiShortcutUeberID(int parteiID) {
        return jsonParserStatic.parseParteiShortcut(parteiID);
    }

    public void setParteiShortcut(String parteiShortcut) {
        this.shortcut = parteiShortcut;
    }

    public static String getParteiNameUeberID(int parteiID) {
        return jsonParserStatic.parseParteiName(parteiID);
    }

    public void setParteiName(String parteiName) {
        this.name = parteiName;
    }

}
