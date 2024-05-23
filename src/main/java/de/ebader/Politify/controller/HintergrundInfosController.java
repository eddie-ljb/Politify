package de.ebader.Politify.controller;

import de.ebader.Politify.controller.filePersistence.JsonParserLokal;

public class HintergrundInfosController implements ControllerInfos {

    private JsonParserLokal jsonParser = new JsonParserLokal();

    public String getParteiInfos(String parteiShortcut) {
        return jsonParser.parseParteiInfos(parteiShortcut);
    }

    public String getParlamentInfos(String parlamentShortcut) {
        return jsonParser.parseParlamentInfos(parlamentShortcut);
    }

    public String getWahlenInfos(String parlamentShortcut) {
        return jsonParser.parseWahlenInfos(parlamentShortcut);
    }

    public String getRegierungInfos(String infoArt) {
        return jsonParser.parseRegierungInfos(infoArt);
    }

}