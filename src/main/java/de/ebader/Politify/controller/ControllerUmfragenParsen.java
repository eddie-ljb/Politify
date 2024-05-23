package de.ebader.Politify.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ControllerUmfragenParsen {

    public Map<String, String> getBestimmteUmfrageErgebnisse(Integer umfrageID);

    public Map<String, String> getNeuesteUmfrageErgebnisse();

    public List<Integer> getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Integer parlamentID);

    public String getSchoeneAusgabeStringFuerBestimmteUmfrageErgebnisse(Map<String, String> umfrageErgebnisse);

    public Set<String> getAlleParteienShortcutsVonUmfrageErgebnisse(Map<String, String> umfrageErgebnisse);

    public Map<String, Double> getAlleParteienErgebnisseImParlamentMitFuenfProzentHuerde(
        Map<String, String> umfrageErgebnisse, Set<String> umfrageKeys, String parlament);

    public double getSperrKlauselImParlament(String parlament);

    public String getSchoeneAusgabeStringFuerBestimmteUmfrageErgebnisseBeiProzentHuerdenParlament(
        Map<String, Double> umfrageErgebnisse);

    public List<List<String>> berechneKoalitionen(Map<String, String> umfrageErgebnisse, Set<String> umfrageKeys);

    public List<List<String>> berechneKoalitionenMitSperrKlausel(Map<String, Double> umfrageErgebnisse,
        Set<String> umfrageKeys);

    public Set<String> getAlleParteienShortcutsVonUmfrageErgebnisseMitSperrKlausel(
        Map<String, Double> umfrageErgebnisseVonParlament);

    public boolean enthaeltListe(List<List<String>> koalitionen, List<String> beispielkoalition);

    public Map<String, Integer> berechneSitzzahlenDerParteienMitSperrKlausel(Map<String, Double> umfrageErgebnisse,
        Set<String> umfrageKeys, String parlamentShortcut);

    public Map<String, String> getParlamentInfos(Integer parlamentID);

    public Map<String, String> getLizenzInfos();

    public Map<String, String> getUmfrageInfos(Integer umfrageID);

    public int getMaxSitzeImParlament(String parlament);

    public Map<String, Double> berechneDurchschnittsergebnisse(Integer parlamentID);

    public List<List<String>> berechneKoalitionenMitSperrKlauselDurchSitzzahlen(
        Map<String, Integer> sitzzahlenDerParteien);

}
