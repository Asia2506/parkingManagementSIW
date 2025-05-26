package siw.uniroma3.parkingManagementSIW.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.service.DescrizioneTesseraService;
import siw.uniroma3.parkingManagementSIW.service.TesseraService;

@Controller
public class TesseraController {
	
	@Autowired
	TesseraService tesseraService;
	
	@Autowired
	DescrizioneTesseraService tipoTesseraService;

	
	@GetMapping("/tessera/{id}")
	public String getTessera(@PathVariable("id") Long id, Model model) {		 
		model.addAttribute("tessera", this.tesseraService.getTesseraById(id));
		return "tessera.html";	
	}

	
	@GetMapping("/tessereEmesse")
	public String showTessereEmesse(Model model) {
		model.addAttribute("tessereEmesse", this.tesseraService.getAllTessere());
		return "tessereEmesse.html";		
	 }
	
	 
	  
	@GetMapping("/cercaTitolareTessera/{numeroTessera}")
	public String findTitolare(@PathVariable("numeroTessera") Long id,Model model) {
		model.addAttribute("tessera",this.tesseraService.getTesseraById(id));
		return "formRicercaTitolareTessera.html";
	}
	
	@GetMapping("/nuovoTitolareTessera")
	public String newTitolare(Model model) {
		return "formNewCliente.html";
	}
	 
	
	@PostMapping("/emissioneTessera/associaAnagrafica")
	public String newTessera(@ModelAttribute("tessera") Tessera tessera,@RequestParam("descrizioneTesseraId") Long descrizioneTesseraId,Model model) {
		//se la tessera non esiste oppure se esiste e non ha un titolare
		if(!this.tesseraService.existsById(tessera.getNumero()) ||
			(this.tesseraService.existsById(tessera.getNumero()) && 
			 this.tesseraService.getTesseraById(tessera.getNumero()).getTitolare()==null)) {
			//setta tutti i parametri tranne il titolare
			tessera.setDataEmissione(LocalDate.now());
			tessera.setDescrizioneTessera(this.tipoTesseraService.getDescrizioneTesseraById(descrizioneTesseraId));
			if(tessera.getDescrizioneTessera().getTipoTessera().equals("FULL"))
				tessera.setDataScadenza(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));			//imposta la scadenza all'ultimo giorno del mese
			//salva la tessera nel db 
			this.tesseraService.save(tessera);
			model.addAttribute("tessera",tessera);
			return "formAssociaAnagrafica.html";
			
		}
		model.addAttribute("tipiTessere",this.tipoTesseraService.getAllDescrizioneTessera());
		return "emissioneTessera.html";
	}
	
	
	@GetMapping("/emissioneTessera/associaAnagrafica/{numeroTessera}")
	public String associaAnagrafica(@PathVariable("numeroTessera") Long id,Model model) {
		
		model.addAttribute("tessera",this.tesseraService.getTesseraById(id));
		return "formAssociaAnagrafica.html";
			
	}
}
