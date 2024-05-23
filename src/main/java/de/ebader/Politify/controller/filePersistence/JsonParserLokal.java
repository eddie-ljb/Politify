package de.ebader.Politify.controller.filePersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ebader.Politify.controller.dao.JsonParserAPI;
import de.ebader.Politify.util.Util;

public class JsonParserLokal {

    Util util = new Util();

    public static JsonNode jsonNode;
    public ObjectMapper objectmapper;
    @SuppressWarnings("unused")
    private JsonParserAPI jsonParser = new JsonParserAPI();

    @SuppressWarnings("static-access")
    public JsonParserLokal() {
        this.objectmapper = new ObjectMapper();

        if (this.jsonNode == null) {
            jsonNode = baueResponseAuf();

        }
    }

    public void refresheJsonNode() {
        this.jsonNode = baueResponseAuf();
    }


    private JsonNode baueResponseAuf() {

        String dateiPfad = util.getLocaleJsonPath("InfoDateien/info.json");

        String jsonContent = "";
        try {
            jsonContent = new String(Files.readAllBytes(Paths.get(dateiPfad)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        try {
            jsonNode = objectmapper.readTree(jsonContent);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    public String parseParteiInfos(String parteiShortcut) {
        return jsonNode.get("Partei").get(parteiShortcut).asText();
    }

    public String parseParlamentInfos(String parlamentShortcut) {
        return jsonNode.get("Parlament").get(parlamentShortcut).asText();
    }

    public String parseWahlenInfos(String parlamentShortcut) {
        return jsonNode.get("Wahlen").get(parlamentShortcut).asText();
    }

    public String parseRegierungInfos(String infoArt) {
        return jsonNode.get("Regierung").get(infoArt).asText();
    }

    public String parseHilfeHomePage() {
        return jsonNode.get("Hilfen").get("HomePage").asText();
    }

    public String parseHilfeToolUmfragenParsen(String infoArt) {
        return jsonNode.get("Hilfen").get("ToolUmfragenParsen").get(infoArt).asText();
    }

    public String parseHilfeToolEigenePrognosen(String infoArt) {
        return jsonNode.get("Hilfen").get("ToolEigenePrognosen").get(infoArt).asText();
    }

    public String parseHilfeToolHintergrundInfos(String infoArt) {
        return jsonNode.get("Hilfen").get("ToolHintergrundInfos").get(infoArt).asText();
    }

    public String parseHilfeToolWahlo() {
        return jsonNode.get("Hilfen").get("ToolWahlO").asText();
    }

    public String parseFragenInhalt(Integer frageID) {
        String fragenname = "Frage" + frageID;
        return jsonNode.get("Wahl-O-Meter").get("Fragen").get(fragenname).get("Frageinhalt").asText();
    }

    public String parseAntwort1(Integer frageID) {
        String fragenname = "Frage" + frageID;
        return jsonNode.get("Wahl-O-Meter").get("Fragen").get(fragenname).get("Antwort1").asText();
    }

    public String parseAntwort2(Integer frageID) {
        String fragenname = "Frage" + frageID;
        return jsonNode.get("Wahl-O-Meter").get("Fragen").get(fragenname).get("Antwort2").asText();
    }

    public String parseAntwort3(Integer frageID) {
        String fragenname = "Frage" + frageID;
        return jsonNode.get("Wahl-O-Meter").get("Fragen").get(fragenname).get("Antwort3").asText();
    }

    public String parseAuswertung(String politischeAusrichtung) {
        return jsonNode.get("Wahl-O-Meter").get("Auswertung").get(politischeAusrichtung).asText();
    }

}
