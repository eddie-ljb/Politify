package de.ebader.Politify.model;

import de.ebader.Politify.controller.dao.JsonParserAPI;

public class License {

    private String lizenzName;
    private String lizenzShortcut;
    private String autor;
    private String publisher;
    private static JsonParserAPI jsonParser = new JsonParserAPI();

    public License() {
        this.lizenzName = jsonParser.parseLicenseName();
        this.lizenzShortcut = jsonParser.parseLicenseShortcut();
        this.autor = jsonParser.parseAutor();
        this.publisher = jsonParser.parsePublisher();
    }

    public String getShortcut() {
        return this.lizenzShortcut;
    }

    public String getName() {
        return this.lizenzName;
    }

    public String getAutor() {
        return this.autor;
    }

    public String getPublisher() {
        return this.publisher;
    }


}
