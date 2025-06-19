package siw.uniroma3.parkingManagementSIW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import siw.uniroma3.parkingManagementSIW.service.OperazioneService;

@Controller
public class MainController {
		
		@Autowired
		OperazioneService operazioneService;
	
	
		@GetMapping("/")
		public String index(Model model) {
			model.addAttribute("cancella",false);
			model.addAttribute("operazioniOggi",this.operazioneService.getAllOperazioniDiOggi());
			return "home.html";	
		}
}
