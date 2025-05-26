package siw.uniroma3.parkingManagementSIW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import siw.uniroma3.parkingManagementSIW.repository.DipendenteCCRepository;

@Controller
public class DipendenteCCController {
	
	@Autowired
	DipendenteCCRepository dipendenteCCRepository;
	
	@GetMapping("/ricercaCliente")
	private String findCliente(@RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "cognome", required = false) String cognome,Model model) {
		
		model.addAttribute("clientiTrovati", this.dipendenteCCRepository.findByNomeAndCognome(nome, cognome));
		
		return "formRicercaTitolareTessera.html";
	}
}
