package de.ebader.Politify.model;

import de.ebader.Politify.controller.dao.JsonParserAPI;

public class Institut {

    private int institutID;
    @SuppressWarnings("unused")
    private String institutName;
    private static JsonParserAPI jsonParser = new JsonParserAPI();

    public Institut(int institutID) {
        this.institutID = institutID;
        this.institutName = jsonParser.parseInstitutName(institutID);
    }

    public int getInstitutID() {
        return institutID;
    }

    public void setInstitutID(int institutID) {
        this.institutID = institutID;
    }

    public String getInstitutName() {
        return jsonParser.parseInstitutName(institutID);
    }

    public void setInstitutName(String institutName) {
        this.institutName = institutName;
    }
}