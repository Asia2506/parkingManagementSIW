package siw.uniroma3.parkingManagementSIW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.service.TesseraService;

@Controller
public class TesseraController {
	
	@Autowired
	TesseraService tesseraService;

	
	@GetMapping("/tessera/{id}")
	public String getMovie(@PathVariable("id") Long id, Model model) {		 
		
		model.addAttribute("tessera", this.tesseraService.getTesseraById(id));
		return "tessera.html";	
	}

	@GetMapping("/tessereEmesse")
	public String showTessereEmesse(Model model) {
		model.addAttribute("tessereEmesse", this.tesseraService.getAllTessere());
		return "tessereEmesse.html";		
	 }
	
	  @GetMapping("/emissioneTessera")
	  public String emettiNuovaTessera(Model model) {
		  model.addAttribute("tessera", new Tessera());
		  return "emissioneTessera.html";
	  }
}
