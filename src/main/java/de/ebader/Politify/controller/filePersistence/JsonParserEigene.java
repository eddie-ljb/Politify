package de.ebader.Politify.controller.filePersistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ebader.Politify.model.Parlamente;
import de.ebader.Politify.util.Util;

public class JsonParserEigene {

    Util util = new Util();
    String dateiPfad = util.getLocaleJsonPath("eigenePrognosen.json");
    String jsonContent = "";

    private ObjectMapper objectMapper = new ObjectMapper();
    public JsonNode jsonNode;

    public JsonParserEigene() {
        this.objectMapper = new ObjectMapper();

        if (this.jsonNode == null) {
            jsonNode = baueResponseAuf();

        }
    }

    public void refresheJsonNode() {
        this.jsonNode = baueResponseAuf();
    }

    public JsonNode baueResponseAuf() {
        String dateiName = "eigenePrognosen.json";
        this.dateiPfad = System.getProperty("user.home") + File.separator + dateiName;

        try {
            if (!Files.exists(Paths.get(dateiPfad))) {
                // Die Datei existiert nicht, erstelle sie und fülle sie mit dem
                // jsonContent
                createAndWriteFile(dateiPfad);
            }
            else {
                // Die Datei existiert, lese sie ein
                String jsonContent = new String(Files.readAllBytes(Paths.get(dateiPfad)));
                jsonNode = objectMapper.readTree(jsonContent);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return jsonNode;
    }

    private void createAndWriteFile(String dateiPfad) throws IOException {
        // Hier wird die Datei erstellt und mit dem jsonContent befüllt
        // Beispiel: Annahme, dass jsonContent bereits vorhanden ist

        Files.write(Paths.get(dateiPfad), jsonContent.getBytes());
        System.out.println("Datei wurde erstellt und mit Inhalt befüllt: " + dateiPfad);
    }

    public List<Integer> getPrognoseIDs() {
        List<Integer> entryNumbers = new ArrayList<>();
        JsonNode entryNode = jsonNode.get("Entry");

        if (entryNode != null && entryNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> entries = entryNode.fields();

            while (entries.hasNext()) {
                Map.Entry<String, JsonNode> entry = entries.next();
                String entryNumber = entry.getKey();
                entryNumbers.add(Integer.parseInt(entryNumber));
            }
        }

        return entryNumbers;
    }

    public Integer parseHoechstePrognoseID() {
        jsonNode = baueResponseAuf();
        List<Integer> entryNumbers = new ArrayList<>();
        JsonNode entryNode = jsonNode.get("Entry");

        if (entryNode != null && entryNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> entries = entryNode.fields();

            while (entries.hasNext()) {
                Map.Entry<String, JsonNode> entry = entries.next();
                String entryNumber = entry.getKey();
                entryNumbers.add(Integer.parseInt(entryNumber));
            }
        }
        Integer hoechsteID = 0;
        for (Integer id : entryNumbers) {
            if (id > hoechsteID) {
                hoechsteID = id;
            }
        }
        System.out.println(hoechsteID);
        return hoechsteID;
    }


    public Map<String, Double> parseErgebnisseAusGespeicherterUmfrage(Integer prognoseID) {
        try {
            Map<String, Double> umfrageErgebnisse = new HashMap<String, Double>();
            List<Integer> parteienIDs = new ArrayList<>();
            JsonNode jsonUmfrageIDs = jsonNode.get("Entry").get(prognoseID.toString()).get("ergebnisseSorted");
            String ergebnisIDs = jsonUmfrageIDs.toString();

            String[] umfrageIDsArray = ergebnisIDs.split(",");
            umfrageIDsArray[0] = umfrageIDsArray[0].replace('{', ' ');
            umfrageIDsArray[umfrageIDsArray.length - 1] = umfrageIDsArray[umfrageIDsArray.length - 1].replace('}', ' ');
            Map<String, String> umfrageIDsMap = new HashMap<>();
            for (int i = 0; i < umfrageIDsArray.length; i++) {
                String[] zwischenSpeicherFuerIDs = umfrageIDsArray[i].split(":");
                zwischenSpeicherFuerIDs[0] = zwischenSpeicherFuerIDs[0].replace('"', ' ');

                String key = zwischenSpeicherFuerIDs[0];
                String trimmedKey = key.replaceAll(" ", "");
                if (trimmedKey.equals("FreieWähler")) {
                    trimmedKey = "Freie Wähler";
                }
                else if (trimmedKey.equals("DiePARTEI")) {
                    trimmedKey = "Die PARTEI";
                }
                String value = zwischenSpeicherFuerIDs[1];
                String trimmedValue = value.replaceAll(" ", "");
                Double valueDouble = Double.valueOf(trimmedValue);
                umfrageErgebnisse.put(trimmedKey, valueDouble);
            }
            return umfrageErgebnisse;
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        System.out.println("fail");
        return null;
    }

    public Parlamente parseParlamentAusGespeicherterUmfrage(Integer prognoseID) {
        Integer parlamentID = jsonNode.get("Entry").get(prognoseID.toString()).get("Parlament_ID").asInt();
        switch (parlamentID) {
            case 0 :
                return Parlamente.BUNDESTAG;
            case 1 :
                return Parlamente.LANDTAG_BW;
            case 2 :
                return Parlamente.LANDTAG_BAY;
            case 3 :
                return Parlamente.ABGEO_BER;
            case 4 :
                return Parlamente.LANDTAG_BRA;
            case 5 :
                return Parlamente.BUERGER_BRE;
            case 6 :
                return Parlamente.BUERGER_HH;
            case 7 :
                return Parlamente.LANDTAG_HES;
            case 8 :
                return Parlamente.LANDTAG_MEPO;
            case 9 :
                return Parlamente.LANDTAG_NIE;
            case 10 :
                return Parlamente.LANDTAG_NRW;
            case 11 :
                return Parlamente.LANDTAG_RP;
            case 12 :
                return Parlamente.LANDTAG_SAAR;
            case 13 :
                return Parlamente.LANDTAG_SACHS;
            case 14 :
                return Parlamente.LANDTAG_SAAN;
            case 15 :
                return Parlamente.LANDTAG_SCHHOL;
            case 16 :
                return Parlamente.LANDTAG_THUE;
            default :
                return Parlamente.EU_PARLAMENT;
        }
    }


}

