package de.ebader.Politify.model;

import de.ebader.Politify.controller.dao.JsonParserAPI;

public class Method {

    private int methodenID;
    @SuppressWarnings("unused")
    private String methodenName;
    private static JsonParserAPI jsonParser = new JsonParserAPI();

    public Method(Integer methodenID) {
        this.methodenID = methodenID;
        this.methodenName = jsonParser.parseMethodenName(methodenID);
    }

    public int getMethodenID() {
        return methodenID;
    }

    public String getMethodenName() {
        return jsonParser.parseMethodenName(methodenID);
    }

}
