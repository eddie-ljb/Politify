package de.ebader.Politify.controller;

public interface ControllerInfos {

    public String getParteiInfos(String parteiShortcut);

    public String getParlamentInfos(String parlamentShortcut);

    public String getWahlenInfos(String parlamentShortcut);

    public String getRegierungInfos(String infoArt);

}
