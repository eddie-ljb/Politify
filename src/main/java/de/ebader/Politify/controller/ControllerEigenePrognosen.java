package de.ebader.Politify.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.ebader.Politify.model.Parlamente;
import de.ebader.Politify.model.Parteien;


public interface ControllerEigenePrognosen {

    public boolean getEnthältListe(List<List<String>> koalitionen, List<String> beispielkoalition);

    public Set<Parteien> erstelleParteiShortcutSetFuerParlament(Integer parlamentID);

    public void getSpeichereInJson(Map<String, Double> ergebnisseSorted, Set<String> keysErgebnisseGefiltert,
        Map<String, Integer> sitzzahlenParteien, Integer parlament_ID);

    public void getLoescheEintragUnterEntry(int eintragNummer);

    public List<Integer> getPrognoseIDsVonGespeichertenPrognose();

    public Parlamente getParlamentVonGespeicherterPrognose(Integer prognoseID);

    public Map<String, Double> getErgebnisseVonGespeicherterPrognose(Integer prognoseID);

    public Integer getHoechstePrognoseIDVonGespeicherten();

    public Integer getPassendePrognoseID();
}
