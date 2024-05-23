package de.ebader.Politify.controller;

import de.ebader.Politify.controller.dao.JsonParserAPI;

public class ZentralerController implements ControllerZentral {

    ControllerEigenePrognosen controllerEigene;
    ControllerInfos controllerInfos;
    ControllerUmfragenParsen controllerUmfragen;
    ControllerWahloMeter controllerWahlo;
    JsonParserAPI jsonParser;

    @Override
    public ControllerEigenePrognosen getPrognosenController() {
        return new EigenePrognosenController(jsonParser);
    }

    @Override
    public ControllerInfos getHintergrundinfosController() {
        return new HintergrundInfosController();
    }

    @Override
    public ControllerUmfragenParsen getUmfragenController() {
        return new UmfragenErhaltenController(jsonParser);
    }

    @Override
    public ControllerWahloMeter getWahlOController() {
        return new WahlometerController();
    }


}
