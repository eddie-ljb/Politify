package de.ebader.Politify.controller;

import java.util.Map;

public interface ControllerWahloMeter {

    public void berechnePunktzahlFuerSpezielleFrage();

    public Map<Integer, String> getAntwortMoeglichkeiten();

    public String getNaechsteFrageInhalt();

    public Integer getPunktzahlGesamt();

    public void setGewaehlteAntwortAktuell(Integer gewaehlteAntwortAtkuell);

    public Integer getFragenID();

    public void setFragenID(Integer fragenID);

    public void setPunktzahl(int i);

    public String getAuswertung();
}
