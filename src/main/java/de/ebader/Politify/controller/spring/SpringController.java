package de.ebader.Politify.controller.spring;

import de.ebader.Politify.controller.ControllerZentral;
import de.ebader.Politify.controller.UmfragenErhaltenController;
import de.ebader.Politify.controller.ZentralerController;
import de.ebader.Politify.controller.dao.JsonParserAPI;
import de.ebader.Politify.model.Parlamente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Set;

@Controller
public class SpringController {
	
	@Value("${spring.application.name}")
	String appName;

	private ControllerZentral zentralerController = new ZentralerController();


	@GetMapping(value = "/")
	public ModelAndView showHomepage(Model model) {
		model.addAttribute("appName", appName);

		JsonParserAPI jsonParser = new JsonParserAPI();

		UmfragenErhaltenController umfragenController = new UmfragenErhaltenController(jsonParser);
		int umfrageID = umfragenController.getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Parlamente.BUNDESTAG.getParlamentID()).get(0);
		System.out.println(umfrageID);
		Map<String, String> umfrageErgebnisse  = umfragenController.getBestimmteUmfrageErgebnisse(umfrageID);
		Set<String> parteien = umfrageErgebnisse.keySet();


		model.addAttribute("parteien", parteien);
		model.addAttribute("umfrageErgebnisse", umfrageErgebnisse);

		return new ModelAndView("index.html");
	}
	@GetMapping(value = "/umfragen")
	public ModelAndView showUmfragenPage(Model model) {
		model.addAttribute("appName", appName);

		JsonParserAPI jsonParser = new JsonParserAPI();

		UmfragenErhaltenController umfragenController = new UmfragenErhaltenController(jsonParser);
		int umfrageID = umfragenController.getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Parlamente.BUNDESTAG.getParlamentID()).get(0);
		System.out.println(umfrageID);
		Map<String, String> umfrageErgebnisse  = umfragenController.getBestimmteUmfrageErgebnisse(umfrageID);
		Set<String> parteien = umfrageErgebnisse.keySet();


		model.addAttribute("parteien", parteien);
		model.addAttribute("umfrageErgebnisse", umfrageErgebnisse);

		return new ModelAndView("umfragen.html");
	}
	@GetMapping(value = "/parlament-info")
	public ModelAndView showParliamentInfoPage(Model model) {
		model.addAttribute("appName", appName);

		JsonParserAPI jsonParser = new JsonParserAPI();

		UmfragenErhaltenController umfragenController = new UmfragenErhaltenController(jsonParser);
		int umfrageID = umfragenController.getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Parlamente.BUNDESTAG.getParlamentID()).get(0);
		System.out.println(umfrageID);
		Map<String, String> umfrageErgebnisse  = umfragenController.getBestimmteUmfrageErgebnisse(umfrageID);
		Set<String> parteien = umfrageErgebnisse.keySet();


		model.addAttribute("parteien", parteien);
		model.addAttribute("umfrageErgebnisse", umfrageErgebnisse);

		return new ModelAndView("parlament-info.html");
	}
	@GetMapping(value = "/parteien-info")
	public ModelAndView showParteienInfoPage(Model model) {
		model.addAttribute("appName", appName);

		JsonParserAPI jsonParser = new JsonParserAPI();

		UmfragenErhaltenController umfragenController = new UmfragenErhaltenController(jsonParser);
		int umfrageID = umfragenController.getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Parlamente.BUNDESTAG.getParlamentID()).get(0);
		System.out.println(umfrageID);
		Map<String, String> umfrageErgebnisse  = umfragenController.getBestimmteUmfrageErgebnisse(umfrageID);
		Set<String> parteien = umfrageErgebnisse.keySet();


		model.addAttribute("parteien", parteien);
		model.addAttribute("umfrageErgebnisse", umfrageErgebnisse);

		return new ModelAndView("parteien-info.html");
	}
	@GetMapping(value = "/aemter-info")
	public ModelAndView showAemterInfoPage(Model model) {
		model.addAttribute("appName", appName);

		JsonParserAPI jsonParser = new JsonParserAPI();

		UmfragenErhaltenController umfragenController = new UmfragenErhaltenController(jsonParser);
		int umfrageID = umfragenController.getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Parlamente.BUNDESTAG.getParlamentID()).get(0);
		System.out.println(umfrageID);
		Map<String, String> umfrageErgebnisse  = umfragenController.getBestimmteUmfrageErgebnisse(umfrageID);
		Set<String> parteien = umfrageErgebnisse.keySet();


		model.addAttribute("parteien", parteien);
		model.addAttribute("umfrageErgebnisse", umfrageErgebnisse);

		return new ModelAndView("aemter-info.html");
	}

	@GetMapping(value = "/sonstiges-info")
	public ModelAndView showSonstigesInfoPage(Model model) {
		model.addAttribute("appName", appName);

		JsonParserAPI jsonParser = new JsonParserAPI();

		UmfragenErhaltenController umfragenController = new UmfragenErhaltenController(jsonParser);
		int umfrageID = umfragenController.getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Parlamente.BUNDESTAG.getParlamentID()).get(0);
		System.out.println(umfrageID);
		Map<String, String> umfrageErgebnisse  = umfragenController.getBestimmteUmfrageErgebnisse(umfrageID);
		Set<String> parteien = umfrageErgebnisse.keySet();


		model.addAttribute("parteien", parteien);
		model.addAttribute("umfrageErgebnisse", umfrageErgebnisse);

		return new ModelAndView("sonstiges.html");
	}

	@GetMapping(value = "/prognosen")
	public ModelAndView showPrognosenPage(Model model) {
		model.addAttribute("appName", appName);

		JsonParserAPI jsonParser = new JsonParserAPI();

		UmfragenErhaltenController umfragenController = new UmfragenErhaltenController(jsonParser);
		int umfrageID = umfragenController.getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Parlamente.BUNDESTAG.getParlamentID()).get(0);
		System.out.println(umfrageID);
		Map<String, String> umfrageErgebnisse  = umfragenController.getBestimmteUmfrageErgebnisse(umfrageID);
		Set<String> parteien = umfrageErgebnisse.keySet();


		model.addAttribute("parteien", parteien);
		model.addAttribute("umfrageErgebnisse", umfrageErgebnisse);

		return new ModelAndView("prognosen.html");
	}

	@GetMapping(value = "/wahlomat")
	public ModelAndView showWahlomatPage(Model model) {
		model.addAttribute("appName", appName);

		JsonParserAPI jsonParser = new JsonParserAPI();

		UmfragenErhaltenController umfragenController = new UmfragenErhaltenController(jsonParser);
		int umfrageID = umfragenController.getUmfrageIDsVonNeuerenUmfragenFuerBestimmtesParlament(Parlamente.BUNDESTAG.getParlamentID()).get(0);
		System.out.println(umfrageID);
		Map<String, String> umfrageErgebnisse  = umfragenController.getBestimmteUmfrageErgebnisse(umfrageID);
		Set<String> parteien = umfrageErgebnisse.keySet();


		model.addAttribute("parteien", parteien);
		model.addAttribute("umfrageErgebnisse", umfrageErgebnisse);

		return new ModelAndView("wahlomat.html");
	}

	public ControllerZentral getZentralerController() {
		return zentralerController;
	}

	public void setZentralerController(ControllerZentral zentralerController) {
		this.zentralerController = zentralerController;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
