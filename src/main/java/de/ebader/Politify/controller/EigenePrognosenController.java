package de.ebader.Politify.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.ebader.Politify.controller.dao.JsonParserAPI;
import de.ebader.Politify.controller.filePersistence.JsonParserEigene;
import de.ebader.Politify.model.Parlamente;
import de.ebader.Politify.model.Parteien;
import de.ebader.Politify.util.Util;

public class EigenePrognosenController implements ControllerEigenePrognosen {

    JsonParserAPI jsonParser;
    Util util = new Util();

    public EigenePrognosenController(JsonParserAPI jsonParser) {
        this.jsonParser = jsonParser;
    }
    // Verwende den relativen Pfad im Verhältnis zu diesem Basisverzeichnis
    final URL url = util.getURL("eigenePrognosen.json");

    private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    JsonParserEigene jsonParserEigene = new JsonParserEigene();


    @Override
    public boolean getEnthältListe(List<List<String>> koalitionen, List<String> beispielkoalition) {
        for (List<String> vorhandeneListe : koalitionen) {
            if (sindListenGleich(vorhandeneListe, beispielkoalition)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void getSpeichereInJson(Map<String, Double> ergebnisseSorted, Set<String> keysErgebnisseGefiltert,
        Map<String, Integer> sitzzahlenParteien, Integer parlament_ID) {
        try {
            File datei = new File(System.getProperty("user.home") + File.separator + "eigenePrognosen.json");
            Map<String, Object> existierendeDaten;

            // Überprüfe, ob die Datei existiert und nicht leer ist
            if (datei.exists() && datei.length() > 0) {
                // Lese die vorhandenen Einträge aus der Datei
                existierendeDaten = objectMapper.readValue(datei, Map.class);
            }
            else {
                // Falls die Datei leer oder nicht vorhanden ist, initialisiere
                // eine neue Map
                existierendeDaten = new HashMap<>();
            }

            // Erstelle einen neuen Eintrag mit fortlaufender Zahl
            int neuerEintragNummer = getPassendePrognoseID();

            Map<String, Object> neuerEintrag = new HashMap<>();
            neuerEintrag.put("sitzzahlenParteien", sitzzahlenParteien);
            neuerEintrag.put("ergebnisseSorted", ergebnisseSorted);
            neuerEintrag.put("Parlament_ID", parlament_ID);
            neuerEintrag.put("keysErgebnisseGefiltert", keysErgebnisseGefiltert);

            // Füge den neuen Eintrag zum "Entry"-Key hinzu
            Map<String, Object> entryMap = (Map<String, Object>) existierendeDaten.computeIfAbsent("Entry",
                k -> new HashMap<>());
            entryMap.put(String.valueOf(neuerEintragNummer), neuerEintrag);

            // Schreibe die aktualisierten Daten zurück in die Datei
            objectMapper.writeValue(datei, existierendeDaten);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getLoescheEintragUnterEntry(int eintragNummer) {
        try {
            File datei = new File(System.getProperty("user.home") + File.separator + "eigenePrognosen.json");

            // Überprüfe, ob die Datei existiert
            if (datei.exists()) {
                Map<String, Object> existierendeDaten = objectMapper.readValue(datei, Map.class);

                // Überprüfe, ob "Entry" in den existierenden Daten vorhanden
                // ist
                if (existierendeDaten.containsKey("Entry")) {
                    Map<String, Object> entryMap = (Map<String, Object>) existierendeDaten.get("Entry");

                    // Überprüfe, ob der angegebene Eintrag in der Entry-Map
                    // vorhanden ist
                    String eintragNummerKey = String.valueOf(eintragNummer);
                    if (entryMap.containsKey(eintragNummerKey)) {
                        // Lösche den Eintrag
                        entryMap.remove(eintragNummerKey);

                        // Schreibe die aktualisierten Daten zurück in die Datei
                        objectMapper.writeValue(datei, existierendeDaten);
                        System.out.println(
                            "Eintrag unter Entry mit der Nummer " + eintragNummer + " erfolgreich gelöscht.");
                    }

                }

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Integer> getPrognoseIDsVonGespeichertenPrognose() {
        return jsonParserEigene.getPrognoseIDs();
    }

    public Map<String, Double> getErgebnisseVonGespeicherterPrognose(Integer prognoseID) {
        return jsonParserEigene.parseErgebnisseAusGespeicherterUmfrage(prognoseID);
    }

    public Parlamente getParlamentVonGespeicherterPrognose(Integer prognoseID) {
        return jsonParserEigene.parseParlamentAusGespeicherterUmfrage(prognoseID);
    }


    public static boolean sindListenGleich(List<String> liste1, List<String> liste2) {
        return liste1.size() == liste2.size() && liste1.containsAll(liste2) && liste2.containsAll(liste1);
    }

    public Integer getHoechstePrognoseIDVonGespeicherten() {
        return jsonParserEigene.parseHoechstePrognoseID();
    }

    public Integer getPassendePrognoseID() {
        jsonParserEigene.refresheJsonNode();
        try {
            File datei = new File(System.getProperty("user.home") + File.separator + "eigenePrognosen.json");
            Map<String, Object> existierendeDaten = null;

            // Überprüfe, ob die Datei existiert und nicht leer ist
            if (datei.exists() && datei.length() > 0) {
                // Lese die vorhandenen Einträge aus der Datei
                existierendeDaten = objectMapper.readValue(datei, Map.class);
            }

            else {
                // Falls die Datei leer oder nicht vorhanden ist, initialisiere
                // eine neue Map
                existierendeDaten = new HashMap<>();
            }
            int neuerEintragNummer = 0;
            List<Integer> ids = getPrognoseIDsVonGespeichertenPrognose();
            Integer maxValue = getHoechstePrognoseIDVonGespeicherten();
            for (Integer i = 0; i < maxValue + 1; i++) {
                if (!ids.contains(i)) {
                    neuerEintragNummer = i;
                    break;
                }
                else {
                    neuerEintragNummer = maxValue + 1;

                }
            }

            return neuerEintragNummer;
        }
        catch (IOException e) {
            return null;
        }
    }

    public Set<Parteien> erstelleParteiShortcutSetFuerParlament(Integer parlamentID) {

        Set<Parteien> parteien = new HashSet<>();

        if (parlamentID == 0) {
            parteien.add(Parteien.CDUCSU);
            parteien.add(Parteien.SSW);
            parteien.add(Parteien.FW);
        }
        else if (parlamentID == 2) {
            parteien.add(Parteien.CSU);
            parteien.add(Parteien.BAYERNPARTEI);
            parteien.add(Parteien.FW);
        }
        else if (parlamentID == 12) {
            parteien.add(Parteien.CDU);
            parteien.add(Parteien.BUNT_SAAR);
            parteien.add(Parteien.FW);
        }
        else if (parlamentID == 15) {
            parteien.add(Parteien.CDU);
            parteien.add(Parteien.SSW);
            parteien.add(Parteien.FW);
        }
        else if (parlamentID == 16) {
            parteien.add(Parteien.CDU);
            parteien.add(Parteien.BFTH);
            parteien.add(Parteien.FW);
        }
        else if (parlamentID == 17) {
            parteien.add(Parteien.CDUCSU);
            parteien.add(Parteien.FW);
        }
        else if (parlamentID == 14) {
            parteien.add(Parteien.CDU);
            parteien.add(Parteien.BVBFW);
        }
        else {
            parteien.add(Parteien.CDU);
            parteien.add(Parteien.FW);
        }

        parteien.add(Parteien.SPD);
        parteien.add(Parteien.GRUENE);
        parteien.add(Parteien.FDP);
        parteien.add(Parteien.AFD);
        parteien.add(Parteien.LINKE);
        parteien.add(Parteien.SONSTIGE);

        parteien.add(Parteien.PIRATEN);
        parteien.add(Parteien.DIEPARTEI);
        parteien.add(Parteien.OEDP);
        parteien.add(Parteien.NPD);
        parteien.add(Parteien.BIW);
        parteien.add(Parteien.VOLT);
        parteien.add(Parteien.TIERSCHUTZPARTEI);

        return parteien;
    }


}
