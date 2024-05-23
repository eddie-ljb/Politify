package de.ebader.Politify.model;

import java.util.Map;

import de.ebader.Politify.controller.dao.JsonParserAPI;

public class Umfrage {

    private int umfrageID;
    private String umfrageDatum;
    private int anzahlBefragtePersonen;
    private int parlamentID;
    private int institutID;
    private int auftraggeberID;
    private int methodenID;
    private static JsonParserAPI jsonParser = new JsonParserAPI();

    /**
     * Generiert eine Umfrage, wenn der Parameter angegeben wird, von der
     * angegeben Umfrage, ansonsten wird die neueste genommen
     * 
     * @param umfrageID
     */
    public Umfrage(int umfrageID) {
        this.umfrageID = umfrageID;
        this.umfrageDatum = jsonParser.parseUmfrageDatum(umfrageID);
        this.anzahlBefragtePersonen = jsonParser.parseAnzahlBefragtePersonenVonUmfrage(umfrageID);
        this.auftraggeberID = jsonParser.parseAuftraggeberIDVonUmfrage(umfrageID);
        this.institutID = jsonParser.parseInstitutIDVonUmfrage(umfrageID);
        this.parlamentID = jsonParser.parseParlamentIDVonUmfrage(umfrageID);
        this.methodenID = jsonParser.parseMethodenIDVonUmfrage(umfrageID);

    }

    /**
     * Generiert eine Umfrage, wenn der Parameter angegeben wird, von der
     * angegeben Umfrage, ansonsten wird die neueste genommen
     * 
     * @param umfrageID
     */
    public Umfrage() {
        this.umfrageID = jsonParser.parseNeuesteUmfrageID();
        this.umfrageDatum = jsonParser.parseUmfrageDatum(jsonParser.parseNeuesteUmfrageID());
        this.anzahlBefragtePersonen = jsonParser.parseAnzahlBefragtePersonenVonUmfrage(
            jsonParser.parseNeuesteUmfrageID());
        this.auftraggeberID = jsonParser.parseAuftraggeberIDVonUmfrage(jsonParser.parseNeuesteUmfrageID());
        this.institutID = jsonParser.parseInstitutIDVonUmfrage(jsonParser.parseNeuesteUmfrageID());
        this.parlamentID = jsonParser.parseParlamentIDVonUmfrage(jsonParser.parseNeuesteUmfrageID());
        this.methodenID = jsonParser.parseMethodenIDVonUmfrage(jsonParser.parseNeuesteUmfrageID());
    }

    public Map<String, String> getErgebnisseVonBestimmterUmfrage(int id) {
        return jsonParser.parseUmfrageErgebnisseUeberID(id);

    }

    public Map<String, String> getErgebnisseVonNeuesterUmfrage() {
        return jsonParser.parseUmfrageErgebnisseUeberID(jsonParser.parseNeuesteUmfrageID());

    }


    public int getUmfrageID() {
        return umfrageID;
    }

    public String getUmfrageDatum() {
        return umfrageDatum;
    }

    public int getAuftraggeberID() {
        return auftraggeberID;
    }

    public int getParlamentID() {
        return parlamentID;
    }

    public int getInstitutID() {
        return institutID;
    }


    public int getMethodenID() {
        return methodenID;
    }

    public int getAnzahlBefragtePersonen() {
        return anzahlBefragtePersonen;
    }

    public int getNeuesteUmfrageID() {
        return jsonParser.parseNeuesteUmfrageID();
    }


}
