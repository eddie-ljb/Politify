package de.ebader.Politify.model;

import de.ebader.Politify.controller.dao.JsonParserAPI;

public enum Parlamente {

    BUNDESTAG(0), LANDTAG_BW(1), LANDTAG_BAY(2), ABGEO_BER(3), LANDTAG_BRA(4), BUERGER_BRE(5), BUERGER_HH(
        6), LANDTAG_HES(7), LANDTAG_MEPO(8), LANDTAG_NIE(9), LANDTAG_NRW(10), LANDTAG_RP(11), LANDTAG_SAAR(
            12), LANDTAG_SACHS(13), LANDTAG_SAAN(14), LANDTAG_SCHHOL(15), LANDTAG_THUE(16), EU_PARLAMENT(17);

    private String parlamentName;
    private String parlamentShortcut;
    private String parlamentsWahl;
    private int parlamentsID;
    private JsonParserAPI jsonParser = new JsonParserAPI();

    private Parlamente(int parlamentsID) {
        this.parlamentsID = parlamentsID;
        this.parlamentName = jsonParser.parseParlamentName(parlamentsID);
        this.parlamentShortcut = jsonParser.parseParlamentShortcut(parlamentsID);
        this.parlamentsWahl = jsonParser.parseParlamentWahl(parlamentsID);
    }

    public int getParlamentID() {
        return parlamentsID;
    }

    public String getParlamentName() {
        return jsonParser.parseParlamentName(parlamentsID);
    }

    public String getParlamentShortcut() {
        return jsonParser.parseParlamentShortcut(parlamentsID);
    }

    public String getParlamentsWahl() {
        return jsonParser.parseParlamentWahl(parlamentsID);
    }

    public String getParlamentsNameUeberID() {
        return jsonParser.parseParlamentName(parlamentsID);
    }

    public String getParlamentsShortcutUeberID() {
        return jsonParser.parseParlamentShortcut(parlamentsID);
    }

    public String getParlamentsWahlUeberID() {
        return jsonParser.parseParlamentWahl(parlamentsID);
    }
}
