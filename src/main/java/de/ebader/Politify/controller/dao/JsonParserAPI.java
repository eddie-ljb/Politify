package de.ebader.Politify.controller.dao;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParserAPI {

    public URI url;
    public Proxy proxy;
    public HttpClient httpClient;
    public HttpRequest request;
    public ObjectMapper objectmapper;
    public static JsonNode jsonNode;

    @SuppressWarnings("static-access")
    public JsonParserAPI() {
        this.url = URI.create("https://api.dawum.de");
        this.httpClient = HttpClient.newBuilder().connectTimeout(
            java.time.Duration.ofSeconds(10)).build();
        this.request = HttpRequest.newBuilder().uri(url).build();
        this.objectmapper = new ObjectMapper();

        if (this.jsonNode == null) {
            try {
                this.jsonNode = baueResponseAuf();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private JsonNode baueResponseAuf()
        throws IOException, InterruptedException, JsonProcessingException, JsonMappingException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // Hier könntest du auch den Response Body oder andere Informationen
        // ausgeben
        String responseBody = response.body();
        jsonNode = objectmapper.readTree(responseBody);
        return jsonNode;
    }

    public String parseLicenseName() {
        try {
            String licenseName = jsonNode.get("Database").get("License").get("Name").asText();
            return licenseName;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String parseLicenseShortcut() {
        try {
            String licenseShortcut = jsonNode.get("Database").get("License").get("Shortcut").asText();
            return licenseShortcut;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String parseAutor() {
        try {
            String autor = jsonNode.get("Database").get("Author").asText();
            return autor;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String parsePublisher() {
        try {
            String publisher = jsonNode.get("Database").get("Publisher").asText();
            return publisher;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String parseParteiShortcut(Integer parteiID) {
        try {
            String parteiShortcut = jsonNode.get("Parties").get(parteiID.toString()).get("Shortcut").asText();
            return parteiShortcut;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String parseParteiName(Integer parteiID) {
        try {
            String parteiName = jsonNode.get("Parties").get(parteiID.toString()).get("Name").asText();
            return parteiName;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer parseNeuesteUmfrageID() {
        Integer highestID = null;
        List<Integer> surveyIDs = new ArrayList<>();
        try {

            for (Integer i = 3600; i < 4000; i++) {
                if (jsonNode.get("Surveys").get(i.toString()).get("Date").asText() != null) {
                    surveyIDs.add(i);
                }
            }
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        highestID = Collections.max(surveyIDs);
        return highestID;
    }


    // Klasse für einen festen ProxySelector
    static class FixedProxySelector extends java.net.ProxySelector {

        private final Proxy proxy;
        public FixedProxySelector(Proxy proxy) {
            this.proxy = proxy;
        }

        @Override
        public List<Proxy> select(URI uri) {
            return List.of(proxy);
        }

        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        }
    }


    @SuppressWarnings("unused")
    public Map<String, String> parseUmfrageErgebnisseUeberID(Integer id) {
        try {
            Map<String, String> umfrageErgebnisse = new HashMap<String, String>();
            List<Integer> parteienIDs = new ArrayList<>();
            JsonNode jsonUmfrageIDs = jsonNode.get("Surveys").get(id.toString()).get("Results");
            String umfrageIDs = jsonUmfrageIDs.toString();

            String[] umfrageIDsArray = umfrageIDs.split(",");
            umfrageIDsArray[0] = umfrageIDsArray[0].replace('{', ' ');
            umfrageIDsArray[umfrageIDsArray.length - 1] = umfrageIDsArray[umfrageIDsArray.length - 1].replace('}', ' ');
            Map<String, String> umfrageIDsMap = new HashMap<>();
            for (int i = 0; i < umfrageIDsArray.length; i++) {
                String[] zwischenSpeicherFuerIDs = umfrageIDsArray[i].split(":");
                zwischenSpeicherFuerIDs[0] = zwischenSpeicherFuerIDs[0].replace('"', ' ');

                String key = zwischenSpeicherFuerIDs[0];
                String trimmedKey = key.replaceAll(" ", "");

                String value = zwischenSpeicherFuerIDs[1];
                String trimmedValue = value.replaceAll(" ", "");
                umfrageErgebnisse.put(parseParteiShortcut(Integer.valueOf(trimmedKey)), trimmedValue);
            }
            return umfrageErgebnisse;
        }
        catch (Exception e) {
        }
        return null;
    }

    public String parseUmfrageDatum(Integer umfrageID) {
        String datum = null;
        try {
            datum = jsonNode.get("Surveys").get(umfrageID.toString()).get("Date").asText();
        }
        catch (Exception e) {
        }
        return datum;
    }

    public Integer parseAnzahlBefragtePersonenVonUmfrage(Integer umfrageID) {
        Integer anzahlBefragtePersonen = null;
        try {
            anzahlBefragtePersonen = jsonNode.get("Surveys").get(umfrageID.toString()).get("Surveyed_Persons").asInt();
        }
        catch (Exception e) {
        }
        return anzahlBefragtePersonen;
    }

    public Integer parseParlamentIDVonUmfrage(Integer umfrageID) {
        Integer parlamentID = null;
        try {
            parlamentID = jsonNode.get("Surveys").get(umfrageID.toString()).get("Parliament_ID").asInt();
        }
        catch (Exception e) {
        }
        return parlamentID;
    }

    public Integer parseInstitutIDVonUmfrage(Integer umfrageID) {
        Integer institutID = null;
        try {
            institutID = jsonNode.get("Surveys").get(umfrageID.toString()).get("Institute_ID").asInt();
        }
        catch (Exception e) {
        }
        return institutID;
    }

    public Integer parseAuftraggeberIDVonUmfrage(Integer umfrageID) {
        Integer auftraggeberID = null;
        try {
            auftraggeberID = jsonNode.get("Surveys").get(umfrageID.toString()).get("Tasker_ID").asInt();
        }
        catch (Exception e) {
        }
        return auftraggeberID;
    }

    public Integer parseMethodenIDVonUmfrage(Integer umfrageID) {
        Integer methodenID = null;
        try {
            methodenID = jsonNode.get("Surveys").get(umfrageID.toString()).get("Method_ID").asInt();
        }
        catch (Exception e) {
        }
        return methodenID;
    }

    public List<Integer> getNeuereUmfrageIDsVonParlamentID(Integer parlamentID) {
        List<Integer> umfrageIDs = new ArrayList<>();

        JsonNode jsonNodeUmfragen;

        for (Integer i = parseNeuesteUmfrageID() - 200; i < 4000; i++) {
            try {
                jsonNodeUmfragen = jsonNode.get("Surveys").get(i.toString());
                Integer vergleichsWertfuerParliamentIDVergleich = jsonNodeUmfragen.get("Parliament_ID").asInt();
                if (vergleichsWertfuerParliamentIDVergleich.compareTo(parlamentID) == 0) {
                    umfrageIDs.add(i);
                }
            }
            catch (Exception e) {
            }
        }
        return umfrageIDs;
    }

    public String parseParlamentName(Integer parlamentID) {
        String parlamentName = null;
        try {
            parlamentName = jsonNode.get("Parliaments").get(parlamentID.toString()).get("Name").asText();
        }
        catch (Exception e) {
        }
        return parlamentName;
    }

    public String parseParlamentShortcut(Integer parlamentID) {
        String parlamentShortcut = null;
        try {
            parlamentShortcut = jsonNode.get("Parliaments").get(parlamentID.toString()).get("Shortcut").asText();
        }
        catch (Exception e) {
        }
        return parlamentShortcut;
    }

    public String parseParlamentWahl(Integer parlamentID) {
        String parlamentsWahl = null;
        try {
            parlamentsWahl = jsonNode.get("Parliaments").get(parlamentID.toString()).get("Election").asText();
        }
        catch (Exception e) {
        }
        return parlamentsWahl;
    }

    public String parseInstitutName(Integer institutID) {
        String institutName = null;
        try {
            institutName = jsonNode.get("Institutes").get(institutID.toString()).get("Name").asText();
        }
        catch (Exception e) {
        }
        return institutName;
    }

    public String parseTaskerName(Integer taskerID) {
        String taskerName = null;
        try {
            taskerName = jsonNode.get("Taskers").get(taskerID.toString()).get("Name").asText();
        }
        catch (Exception e) {
        }
        return taskerName;
    }

    public String parseMethodenName(Integer methodenID) {
        String methodenName = null;
        try {
            methodenName = jsonNode.get("Methods").get(methodenID.toString()).get("Name").asText();
        }
        catch (Exception e) {
        }
        return methodenName;
    }

    @SuppressWarnings("static-access")
    public void refreshJsonNode() {
        try {
            this.jsonNode = baueResponseAuf();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}