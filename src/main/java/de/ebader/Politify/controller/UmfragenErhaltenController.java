package de.ebader.Politify.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import de.ebader.Politify.controller.dao.JsonParserAPI;
import de.ebader.Politify.model.Umfrage;

public class UmfragenErhaltenController implements ControllerUmfragenParsen {

    JsonParserAPI jsonParser;


    public UmfragenErhaltenController(JsonParserAPI jsonParser) {
        this.jsonParser = jsonParser;
    }

    @Override
    public Map<String, String> getBestimmteUmfrageErgebnisse(Integer umfrageID) {
        Map<String, String> umfrageErgebnisse = new TreeMap<>();
        Umfrage umfrage = new Umfrage(umfrageID);
        umfrageErgebnisse = umfrage.getErgebnisseVonBestimmterUmfrage(umfrageID);


        return umfrageErgebnisse;
    }

    @Override
    public Map<String, String> getNeuesteUmfrageErgebnisse() {
        Map<String, String> umfrageErgebnisse = new HashMap<>();
        Umfrage umfrage = new Umfrage();
        umfrageErgebnisse = umfrage.getErgebnisseVonNeuesterUmfrage();
        return umfrageErgebnisse;
    }

    @Override
    public String getSchoeneAusgabeStringFuerBestimmteUmfrageErgebnisse(Map<String, String> umfrageErgebnisse) {
        String ausgabe = null;

        ausgabe = umfrageErgebnisse.toString();

        ausgabe = ausgabe.replace("{", "");
        ausgabe = ausgabe.replace("}", "");
        ausgabe = ausgabe.trim();
        ausgabe = ausgabe.replace(",", ", ");
        ausgabe = ausgabe.trim();
        ausgabe = ausgabe.replace("=", " = ");

        return ausgabe;
    }

    @Override
    public String getSchoeneAusgabeStringFuerBestimmteUmfrageErgebnisseBeiProzentHuerdenParlament(
        Map<String, Double> umfrageErgebnisse) {
        String ausgabe = null;

        ausgabe = umfrageErgebnisse.toString();

        ausgabe = ausgabe.replace("{", "");
        ausgabe = ausgabe.replace("}", "");
        ausgabe = ausgabe.trim();
        ausgabe = ausgabe.replace(",", ", ");
        ausgabe = ausgabe.trim();
        ausgabe = ausgabe.replace("=", " = ");

        return ausgabe;
    }

    @Override
    public Set<String> getAlleParteienShortcutsVonUmfrageErgebnisse(Map<String, String> umfrageErgebnisse) {
        Set<String> umfrageKeys = umfrageErgebnisse.keySet();
        return umfrageKeys;
    }

    public Set<String> getAlleParteienShortcutsVonUmfrageErgebnisseMitSperrKlausel(
        Map<String, Double> umfrageErgebnisseVonParlament) {
        Set<String> umfrageKeys = umfrageErgebnisseVonParlament.keySet();
        return umfrageKeys;
    }

    public Map<String, Double> getAlleParteienErgebnisseImParlamentMitFuenfProzentHuerde(
        Map<String, String> umfrageErgebnisse, Set<String> umfrageKeys, String parlament) {
        Map<String, Double> umfrageErgebnisseNachSperrKlausel = new HashMap<>();
        double sperrklausel = getSperrKlauselImParlament(parlament);
        for (String key : umfrageKeys) {
            double vergleichswert = Double.valueOf(umfrageErgebnisse.get(key));
            if (vergleichswert >= sperrklausel) {

                umfrageErgebnisseNachSperrKlausel.put(key, vergleichswert);
            }
        }
        return umfrageErgebnisseNachSperrKlausel;
    }


    @Override
    public List<List<String>> berechneKoalitionen(Map<String, String> umfrageErgebnisse, Set<String> umfrageKeys) {
        List<List<String>> koalitionen = new ArrayList<>();
        Set<String> umfrageKeysOhneSonstige = new HashSet<>();
        umfrageKeysOhneSonstige.addAll(umfrageKeys);
        umfrageKeysOhneSonstige.remove("Sonstige");

        for (String key : umfrageKeysOhneSonstige) {
            double vergleichswert = Double.valueOf(umfrageErgebnisse.get(key));

            if (vergleichswert >= 50) {
                List<String> koalition = new ArrayList<>();
                koalition.add(key);
                if (!enthaeltListe(koalitionen, koalition)) {
                    koalitionen.add(koalition);
                }

            }
            for (String key2 : umfrageKeysOhneSonstige) {
                double vergleichsparameter = Double.valueOf(umfrageErgebnisse.get(key))
                    + Double.valueOf(umfrageErgebnisse.get(key2));

                if (!key.equals(key2) && vergleichsparameter >= 50) {
                    List<String> koalition = new ArrayList<>();
                    koalition.add(key);
                    koalition.add(key2);
                    if (!enthaeltListe(koalitionen, koalition)) {
                        koalitionen.add(koalition);
                    }

                }
            }
            for (String key2 : umfrageKeysOhneSonstige) {
                double vergleichsparameter = Double.valueOf(umfrageErgebnisse.get(key))
                    + Double.valueOf(umfrageErgebnisse.get(key2));
                if (!key.equals(key2) && vergleichsparameter < 50) {

                    for (String key3 : umfrageKeysOhneSonstige) {
                        double vergleichsparameterDreiParteien = vergleichsparameter
                            + Double.valueOf(umfrageErgebnisse.get(key3));

                        if (!key.equals(key2) && !key.equals(key3) && !key2.equals(key3)
                            && vergleichsparameterDreiParteien >= 50) {
                            List<String> koalition = new ArrayList<>();
                            koalition.add(key);
                            koalition.add(key2);
                            koalition.add(key3);

                            if (!enthaeltListe(koalitionen, koalition)) {
                                koalitionen.add(koalition);
                            }


                        }
                    }
                }
            }
        }


        return koalitionen;
    }

    @Override
    public List<List<String>> berechneKoalitionenMitSperrKlausel(Map<String, Double> umfrageErgebnisse,
        Set<String> umfrageKeys) {
        List<List<String>> koalitionen = new ArrayList<>();
        Set<String> umfrageKeysOhneSonstige = new HashSet<>();
        umfrageKeysOhneSonstige.addAll(umfrageKeys);
        umfrageKeysOhneSonstige.remove("Sonstige");

        for (String key : umfrageKeysOhneSonstige) {
            double vergleichswert = umfrageErgebnisse.get(key);

            if (vergleichswert >= 50) {
                List<String> koalition = new ArrayList<>();
                koalition.add(key);

                if (!enthaeltListe(koalitionen, koalition)) {
                    koalitionen.add(koalition);
                }


            }
            for (String key2 : umfrageKeysOhneSonstige) {
                double vergleichsparameter = umfrageErgebnisse.get(key) + umfrageErgebnisse.get(key2);

                if (!key.equals(key2) && vergleichsparameter >= 50) {
                    List<String> koalition = new ArrayList<>();
                    koalition.add(key);
                    koalition.add(key2);
                    if (!enthaeltListe(koalitionen, koalition)) {
                        koalitionen.add(koalition);
                    }


                }
            }
            for (String key2 : umfrageKeysOhneSonstige) {
                double vergleichsparameter = umfrageErgebnisse.get(key) + umfrageErgebnisse.get(key2);
                if (!key.equals(key2) && vergleichsparameter < 50) {

                    for (String key3 : umfrageKeysOhneSonstige) {
                        double vergleichsparameterDreiParteien = vergleichsparameter + umfrageErgebnisse.get(key3);

                        if (!key.equals(key2) && !key.equals(key3) && !key2.equals(key3)
                            && vergleichsparameterDreiParteien >= 50) {
                            List<String> koalition = new ArrayList<>();
                            koalition.add(key);
                            koalition.add(key2);
                            koalition.add(key3);

                            if (!enthaeltListe(koalitionen, koalition)) {
                                koalitionen.add(koalition);
                            }
                        }
                    }
                }
            }
        }


        return koalitionen;
    }

    @Override
    public List<List<String>> berechneKoalitionenMitSperrKlauselDurchSitzzahlen(
        Map<String, Integer> sitzzahlenDerParteien) {
        List<List<String>> koalitionen = new ArrayList<>();
        Set<String> keySet = sitzzahlenDerParteien.keySet();
        Set<String> umfrageKeysOhneSonstige = new HashSet<>();
        umfrageKeysOhneSonstige.addAll(keySet);


        for (String key : umfrageKeysOhneSonstige) {
            int vergleichswert = sitzzahlenDerParteien.get(key);

            if (vergleichswert >= 315) {
                List<String> koalition = new ArrayList<>();
                koalition.add(key);

                if (!enthaeltListe(koalitionen, koalition)) {
                    koalitionen.add(koalition);
                }


            }
            for (String key2 : umfrageKeysOhneSonstige) {
                int vergleichsparameter = sitzzahlenDerParteien.get(key) + sitzzahlenDerParteien.get(key2);

                if (!key.equals(key2) && vergleichsparameter >= 315) {
                    List<String> koalition = new ArrayList<>();
                    koalition.add(key);
                    koalition.add(key2);
                    if (!enthaeltListe(koalitionen, koalition)) {
                        koalitionen.add(koalition);
                    }


                }
            }
            for (String key2 : umfrageKeysOhneSonstige) {
                int vergleichsparameter = sitzzahlenDerParteien.get(key) + sitzzahlenDerParteien.get(key2);
                if (!key.equals(key2) && vergleichsparameter < 315) {

                    for (String key3 : umfrageKeysOhneSonstige) {
                        int vergleichsparameterDreiParteien = vergleichsparameter + sitzzahlenDerParteien.get(key3);

                        if (!key.equals(key2) && !key.equals(key3) && !key2.equals(key3)
                            && vergleichsparameterDreiParteien >= 315) {
                            List<String> koalition = new ArrayList<>();
                            koalition.add(key);
                            koalition.add(key2);
                            koalition.add(key3);

                            if (!enthaeltListe(koalitionen, koalition)) {
                                koalitionen.add(koalition);
                            }
                        }
                    }
                }
            }
        }


        return koalitionen;
    }

    public Map<String, Double> berechneDurchschnittsergebnisse(Integer parlamentID) {
        Map<String, Double> durchschnittsErgebnisse = new HashMap<>();
        Map<String, String> zwischenErgebnisse = new HashMap<>();
        Set<String> keysFuerZwischenErgebnisse = new HashSet<>();
        Double value = 0.0;
        String key = "";
        Map<String, Integer> zaehler = new HashMap<>();
        int zaehlVariable = 0;
        List<Integer> umfrageIDsFuerParlament = getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(parlamentID);
        List<Integer> umfrageIDsBenoetigt = new ArrayList<>();
        int maxID = umfrageIDsFuerParlament.size() - 1;
        if (maxID < 5) {
            int differenz = 0 + maxID;
            for (int i = maxID - differenz; i < umfrageIDsFuerParlament.size(); i++) {
                if (umfrageIDsFuerParlament.get(i) != null) {
                    umfrageIDsBenoetigt.add(umfrageIDsFuerParlament.get(i));
                }
            }
        }
        else {
            for (int i = maxID - 5; i < umfrageIDsFuerParlament.size(); i++) {
                if (umfrageIDsFuerParlament.get(i) != null) {
                    umfrageIDsBenoetigt.add(umfrageIDsFuerParlament.get(i));
                }
            }
        }

        for (Integer id : umfrageIDsBenoetigt) {
            if (id != null) {

                zwischenErgebnisse = getBestimmteUmfrageErgebnisse(id);
                keysFuerZwischenErgebnisse = getAlleParteienShortcutsVonUmfrageErgebnisse(zwischenErgebnisse);
                for (String keyPartei : keysFuerZwischenErgebnisse) {
                    key = keyPartei;
                    if (durchschnittsErgebnisse.get(key) != null) {
                        value = durchschnittsErgebnisse.get(key) + Double.valueOf(zwischenErgebnisse.get(key));
                        zaehlVariable = zaehler.get(key);
                        zaehlVariable++;
                        zaehler.put(key, zaehlVariable);
                    }
                    else {
                        value = Double.valueOf(zwischenErgebnisse.get(key));
                        zaehlVariable = 0;
                        zaehlVariable++;
                        zaehler.put(keyPartei, zaehlVariable);
                    }
                    durchschnittsErgebnisse.put(key, value);
                    zaehlVariable = 0;
                    value = 0.0;
                    key = "";
                }
            }
            keysFuerZwischenErgebnisse = getAlleParteienShortcutsVonUmfrageErgebnisseMitSperrKlausel(
                durchschnittsErgebnisse);
        }

        for (String keyPartei : keysFuerZwischenErgebnisse) {
            value = (double) Math.round(durchschnittsErgebnisse.get(keyPartei) / zaehler.get(keyPartei));
            key = keyPartei;
            durchschnittsErgebnisse.put(key, value);
        }

        return durchschnittsErgebnisse;
    }

    public boolean enthaeltListe(List<List<String>> listenListe, List<String> zuPruefendeListe) {
        for (List<String> vorhandeneListe : listenListe) {
            if (sindListenGleich(vorhandeneListe, zuPruefendeListe)) {
                return true;
            }
        }
        return false;
    }

    public static boolean sindListenGleich(List<String> liste1, List<String> liste2) {
        return liste1.size() == liste2.size() && liste1.containsAll(liste2) && liste2.containsAll(liste1);
    }

    public int getMaxSitzeImParlament(String parlament) {

        int anzahlAnSitzen = 0;
        switch (parlament) {
            case "Bundestag" :
                anzahlAnSitzen = 630;
                break;
            case "Baden-Württemberg" :
                anzahlAnSitzen = 154;
                break;
            case "Bayern" :
                anzahlAnSitzen = 203;
                break;
            case "Berlin" :
                anzahlAnSitzen = 159;
                break;
            case "Brandenburg" :
                anzahlAnSitzen = 88;
                break;
            case "Bremen" :
                anzahlAnSitzen = 87;
                break;
            case "Hamburg" :
                anzahlAnSitzen = 123;
                break;
            case "Hessen" :
                anzahlAnSitzen = 137;
                break;
            case "Mecklenburg-Vorpommern" :
                anzahlAnSitzen = 79;
                break;
            case "Niedersachsen" :
                anzahlAnSitzen = 146;
                break;
            case "Nordrhein-Westfalen" :
                anzahlAnSitzen = 195;
                break;
            case "Rheinland-Pfalz" :
                anzahlAnSitzen = 101;
                break;
            case "Saarland" :
                anzahlAnSitzen = 51;
                break;
            case "Sachsen" :
                anzahlAnSitzen = 119;
                break;
            case "Sachsen-Anhalt" :
                anzahlAnSitzen = 97;
                break;
            case "Schleswig-Holstein" :
                anzahlAnSitzen = 69;
                break;
            case "Thüringen" :
                anzahlAnSitzen = 90;
                break;
            default :
                // Europawahl
                anzahlAnSitzen = 96;
                break;
        }
        return anzahlAnSitzen;
    }

    public double getSperrKlauselImParlament(String parlament) {

        double sperrklausel = 0;
        switch (parlament) {
            case "Bundestag" :
                sperrklausel = 5.0;
                break;
            case "Baden-Württemberg" :
                sperrklausel = 5.0;
                break;
            case "Bayern" :
                sperrklausel = 5.0;
                break;
            case "Berlin" :
                sperrklausel = 3.0;
                break;
            case "Brandenburg" :
                sperrklausel = 5.0;
                break;
            case "Bremen" :
                sperrklausel = 5.0;
                break;
            case "Hamburg" :
                sperrklausel = 3.0;
                break;
            case "Hessen" :
                sperrklausel = 5.0;
                break;
            case "Mecklenburg-Vorpommern" :
                sperrklausel = 5.0;
                break;
            case "Niedersachsen" :
                sperrklausel = 5.0;
                break;
            case "Nordrhein-Westfalen (NRW)" :
                sperrklausel = 5.0;
                break;
            case "Rheinland-Pfalz" :
                sperrklausel = 5.0;
                break;
            case "Saarland" :
                sperrklausel = 5.0;
                break;
            case "Sachsen" :
                sperrklausel = 5.0;
                break;
            case "Sachsen-Anhalt" :
                sperrklausel = 5.0;
                break;
            case "Schleswig-Holstein" :
                sperrklausel = 5.0;
                break;
            case "Thüringen" :
                sperrklausel = 5.0;
                break;
            default :
                // Europawahl
                sperrklausel = 0.3;
                break;
        }
        return sperrklausel;
    }


    @SuppressWarnings("unused")
    public Map<String, Integer> berechneSitzzahlenDerParteienMitSperrKlausel(Map<String, Double> umfrageErgebnisse,
        Set<String> umfrageKeys, String parlament) {
        Map<String, Integer> sitzZahlenDerParteien = new HashMap<>();
        Set<String> umfrageKeysOhneSonstige = new HashSet<>();
        umfrageKeysOhneSonstige.addAll(umfrageKeys);
        umfrageKeysOhneSonstige.remove("Sonstige");
        int sitzzahl;
        double prozentzahl;
        int anzahlAnSitzen = 0;
        switch (parlament) {
            case "Bundestag" :
                anzahlAnSitzen = 630;
                break;
            case "Baden-Württemberg" :
                anzahlAnSitzen = 154;
                break;
            case "Bayern" :
                anzahlAnSitzen = 203;
                break;
            case "Berlin" :
                anzahlAnSitzen = 159;
                break;
            case "Brandenburg" :
                anzahlAnSitzen = 88;
                break;
            case "Bremen" :
                anzahlAnSitzen = 87;
                break;
            case "Hamburg" :
                anzahlAnSitzen = 123;
                break;
            case "Hessen" :
                anzahlAnSitzen = 137;
                break;
            case "Mecklenburg-Vorpommern" :
                anzahlAnSitzen = 79;
                break;
            case "Niedersachsen" :
                anzahlAnSitzen = 146;
                break;
            case "Nordrhein-Westfalen (NRW)" :
                anzahlAnSitzen = 195;
                break;
            case "Rheinland-Pfalz" :
                anzahlAnSitzen = 101;
                break;
            case "Saarland" :
                anzahlAnSitzen = 51;
                break;
            case "Sachsen" :
                anzahlAnSitzen = 119;
                break;
            case "Sachsen-Anhalt" :
                anzahlAnSitzen = 97;
                break;
            case "Schleswig-Holstein" :
                anzahlAnSitzen = 69;
                break;
            case "Thüringen" :
                anzahlAnSitzen = 90;
                break;
            default :
                // Europawahl
                anzahlAnSitzen = 96;
                break;
        }
        int anteilAnGesamtProzent = 0;
        double zahlAllerProzenteImParlament = 0;

        for (String key : umfrageKeysOhneSonstige) {
            zahlAllerProzenteImParlament += umfrageErgebnisse.get(key);
        }

        for (String key : umfrageKeysOhneSonstige) {
            double faktor = 100 / zahlAllerProzenteImParlament;
            prozentzahl = umfrageErgebnisse.get(key);
            double sitzzahlen = anzahlAnSitzen * ((prozentzahl / 100) * faktor);
            sitzzahl = (int) Math.round(sitzzahlen);
            sitzZahlenDerParteien.put(key, sitzzahl);
        }

        return sitzZahlenDerParteien;
    }

    @Override
    public List<Integer> getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Integer parlamentID) {

        List<Integer> umfrageIDs = jsonParser.getNeuereUmfrageIDsVonParlamentID(parlamentID);
        return umfrageIDs;
    }

    @Override
    public Map<String, String> getParlamentInfos(Integer parlamentID) {

        Map<String, String> parlamentInfos = new HashMap<>();
        parlamentInfos.put("Name", jsonParser.parseParlamentName(parlamentID));
        parlamentInfos.put("Shortcut", jsonParser.parseParlamentShortcut(parlamentID));
        parlamentInfos.put("Wahl", jsonParser.parseParlamentWahl(parlamentID));

        return parlamentInfos;
    }

    @Override
    public Map<String, String> getUmfrageInfos(Integer umfrageID) {

        Map<String, String> parlamentInfos = new HashMap<>();
        parlamentInfos.put("Parlament",
            jsonParser.parseParlamentName(jsonParser.parseParlamentIDVonUmfrage(umfrageID)));
        parlamentInfos.put("Parlament-Shortcut",
            jsonParser.parseParlamentShortcut(jsonParser.parseParlamentIDVonUmfrage(umfrageID)));
        parlamentInfos.put("Date", jsonParser.parseUmfrageDatum(umfrageID));
        parlamentInfos.put("Befragte Personen", jsonParser.parseAnzahlBefragtePersonenVonUmfrage(umfrageID).toString());
        parlamentInfos.put("Institut", jsonParser.parseInstitutName(jsonParser.parseInstitutIDVonUmfrage(umfrageID)));
        parlamentInfos.put("Auftraggeber",
            jsonParser.parseTaskerName(jsonParser.parseAuftraggeberIDVonUmfrage(umfrageID)));
        parlamentInfos.put("Methode", jsonParser.parseMethodenName(jsonParser.parseMethodenIDVonUmfrage(umfrageID)));

        return parlamentInfos;
    }

    @Override
    public Map<String, String> getLizenzInfos() {

        Map<String, String> lizenzInfos = new HashMap<>();

        lizenzInfos.put("Lizenz", jsonParser.parseLicenseShortcut());
        lizenzInfos.put("Autor", jsonParser.parseAutor());
        lizenzInfos.put("Publisher", jsonParser.parsePublisher());

        return lizenzInfos;
    }
}