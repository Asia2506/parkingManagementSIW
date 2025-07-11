package siw.uniroma3.parkingManagementSIW.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import siw.uniroma3.parkingManagementSIW.model.DatiFattura;
import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;
import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.service.DatiFatturaService;
import siw.uniroma3.parkingManagementSIW.service.DipendenteCCService;
import siw.uniroma3.parkingManagementSIW.service.TesseraService;

@Controller
public class DatiFatturaController {
	
	@Autowired
	DatiFatturaService datiFatturaService;
	
	@Autowired
	DipendenteCCService dipendenteCCService;
	
	@Autowired
	TesseraService tesseraService;
	
	
	
	
	@GetMapping("/ricercaDatiFatturazione/{numeroTessera}/{idCliente}")
	private String findCliente(@PathVariable("numeroTessera") Long numeroTessera, // PathVariable for tessera number
			@PathVariable("idCliente") Long id,
			@RequestParam(value = "ragioneSociale", required = false) String ragioneSociale,
			Model model) {
		
		model.addAttribute("datiFattura", new DatiFattura());
		model.addAttribute("dipendenteCC",this.dipendenteCCService.getDIpendenteCCById(id));
		model.addAttribute("tessera",this.tesseraService.getTesseraById(numeroTessera));
		model.addAttribute("datiFatturazione",this.datiFatturaService.getAllDatiFatturaByRagioneSociale(ragioneSociale));
		return "formDatiFatturazione.html";
	}
	
	
	@PostMapping("/creaNuoviDatiFatturazione/{numeroTessera}/{idCliente}")
	private String newDatiFattura(@PathVariable("numeroTessera") Long id,
			@PathVariable("idCliente") Long idCliente,
			@Valid @ModelAttribute("datiFattura") DatiFattura datiFattura,
			BindingResult bindingResult,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("dipendenteCC",this.dipendenteCCService.getDIpendenteCCById(idCliente));
			model.addAttribute("tessera",this.tesseraService.getTesseraById(id));
			model.addAttribute("datiFatturazione",new ArrayList<DatiFattura>());
			return "formDatiFatturazione.html";
		}
		
		DipendenteCC d=this.dipendenteCCService.getDIpendenteCCById(idCliente);
		Tessera t=this.tesseraService.getTesseraById(id);	
		this.datiFatturaService.save(datiFattura);
		
		return "redirect:/emissioneTessera/creaNuovoCliente/associaDatiFatturazione/"+t.getNumero()+"/"+d.getId()+"/"+datiFattura.getId();
	}
	
	
	
	@GetMapping("/datiFatturazione/{idDatiFattura}")
	public String viewDatiFattura(@PathVariable("idDatiFattura") Long id, Model model) {
		model.addAttribute("dati",this.datiFatturaService.getDatiFatturaById(id));
		return "viewDatiFattura.html";
	}

}
