package siw.uniroma3.parkingManagementSIW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import siw.uniroma3.parkingManagementSIW.service.DipendenteCCService;
import siw.uniroma3.parkingManagementSIW.service.TesseraService;

@Controller
public class DipendenteCCController {
	
	@Autowired
	DipendenteCCService dipendenteCCService;
	
	@Autowired
	TesseraService tesseraService;
	
	
	@GetMapping("/ricercaCliente/{numeroTessera}")
	private String findCliente(@PathVariable("numeroTessera") Long id, // PathVariable for tessera number
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cognome", required = false) String cognome,
			Model model) {
		
		model.addAttribute("clientiTrovati", this.dipendenteCCService.getAllDipendentiBy(nome, cognome));
		model.addAttribute("tessera", this.tesseraService.getTesseraById(id));
		return "formRicercaTitolareTessera.html";
	}
}
