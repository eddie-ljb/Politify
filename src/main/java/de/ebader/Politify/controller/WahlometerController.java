package de.ebader.Politify.controller;

import java.util.HashMap;
import java.util.Map;

import de.ebader.Politify.controller.filePersistence.JsonParserLokal;


public class WahlometerController implements ControllerWahloMeter {

    private Integer punktzahlGesamt = 0;
    private Integer fragenID = 0;
    private Integer gewaehlteAntwortAtkuell = 0;
    JsonParserLokal jsonParser = new JsonParserLokal();

    @Override
    public void berechnePunktzahlFuerSpezielleFrage() {
        punktzahlGesamt += gewaehlteAntwortAtkuell;
    }


    @Override
    public String getNaechsteFrageInhalt() {
        fragenID++;
        return jsonParser.parseFragenInhalt(fragenID);
    }

    @Override
    public Map<Integer, String> getAntwortMoeglichkeiten() {
        Map<Integer, String> antwortMoeglichkeiten = new HashMap<>();
        antwortMoeglichkeiten.put(1, jsonParser.parseAntwort1(fragenID));
        antwortMoeglichkeiten.put(2, jsonParser.parseAntwort2(fragenID));
        antwortMoeglichkeiten.put(3, jsonParser.parseAntwort3(fragenID));
        return antwortMoeglichkeiten;
    }

    @Override
    public Integer getPunktzahlGesamt() {
        return punktzahlGesamt;
    }

    @Override
    public void setGewaehlteAntwortAktuell(Integer gewaehlteAntwortAtkuell) {
        this.gewaehlteAntwortAtkuell = gewaehlteAntwortAtkuell;
    }

    @Override
    public Integer getFragenID() {
        return fragenID;
    }

    @Override
    public void setFragenID(Integer fragenID) {
        this.fragenID = fragenID;
    }

    @Override
    public void setPunktzahl(int i) {
        this.punktzahlGesamt = i;
    }

    @Override
    public String getAuswertung() {
        if (punktzahlGesamt >= 30 && punktzahlGesamt < 46) {
            return jsonParser.parseAuswertung("Politisch-Linke");
        }
        else if (punktzahlGesamt >= 46 && punktzahlGesamt < 62) {
            return jsonParser.parseAuswertung("Mitte-Links");
        }
        else if (punktzahlGesamt >= 62 && punktzahlGesamt < 67) {
            return jsonParser.parseAuswertung("Liberal");
        }
        else if (punktzahlGesamt >= 67 && punktzahlGesamt < 75) {
            return jsonParser.parseAuswertung("Mitte/Konservativ");
        }
        else if (punktzahlGesamt >= 75 && punktzahlGesamt < 79) {
            return jsonParser.parseAuswertung("Mitte/Konservativ-Rechts");
        }
        else if (punktzahlGesamt >= 79 && punktzahlGesamt < 91) {
            return jsonParser.parseAuswertung("Poltisch rechts-Nationalistisch");
        }
        else {
            return null;
        }

    }


}
