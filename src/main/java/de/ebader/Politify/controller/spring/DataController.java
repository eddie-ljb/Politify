package de.ebader.Politify.controller.spring;

import de.ebader.Politify.controller.ControllerUmfragenParsen;
import de.ebader.Politify.controller.ControllerZentral;
import de.ebader.Politify.controller.UmfragenErhaltenController;
import de.ebader.Politify.controller.ZentralerController;
import de.ebader.Politify.controller.dao.JsonParserAPI;
import de.ebader.Politify.model.Parlamente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/data")
public class DataController {

    @Value("${spring.application.name}")
    String appName;

    private ControllerZentral zentralerController = new ZentralerController();

    JsonParserAPI jsonParser = new JsonParserAPI();

    UmfragenErhaltenController umfragenController = new UmfragenErhaltenController(jsonParser);


    @GetMapping(value = "/getUmfageIDsFuerParlament")
    @ResponseBody
    public List<Integer> getUmfrageIDsEndpunkt(@RequestParam String parlament) {
        int parlamentID = Integer.valueOf(parlament);
        return umfragenController.getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(parlamentID);
    }

    @GetMapping(value = "/getSperrKlausurFuerUmfrage")
    @ResponseBody
    public double getSperrKlausurFuerUmfrage(@RequestParam String umfrage) {
        int umfrageID = Integer.valueOf(umfrage);
        Map<String, String> umfrageInfos = umfragenController.getUmfrageInfos(umfrageID);
        return umfragenController.getSperrKlauselImParlament(umfrageInfos.get("Parlament-Shortcut"));
    }

    @GetMapping(value = "/getUmfageErgebnisseFuerUmfrageID")
    @ResponseBody
    public Map<String, String> getUmfrageErgebnisseVonUmfrageID(@RequestParam String umfrage) {
        int umfrageID = Integer.valueOf(umfrage);
        return umfragenController.getBestimmteUmfrageErgebnisse(umfrageID);
    }

    @GetMapping(value = "/getParteienFuerUmfrageID")
    @ResponseBody
    public Set<String> getParteienVonUmfrageID(@RequestParam String umfrage) {
        int umfrageID = Integer.valueOf(umfrage);
        return umfragenController.getBestimmteUmfrageErgebnisse(umfrageID).keySet();
    }

    @GetMapping(value = "/getUmfrageDetailsFuerUmfrageID")
    @ResponseBody
    public Map<String, String> getUmfrageDetailsVonUmfrageID(@RequestParam String umfrage) {
        int umfrageID = Integer.valueOf(umfrage);
        return umfragenController.getUmfrageInfos(umfrageID);
    }

    @GetMapping(value = "/getUmfrageDetailsKeySetFuerUmfrageID")
    @ResponseBody
    public Set<String> getUmfrageDetailsKeySetVonUmfrageID(@RequestParam String umfrage) {
        int umfrageID = Integer.valueOf(umfrage);
        return umfragenController.getUmfrageInfos(umfrageID).keySet();
    }
}
