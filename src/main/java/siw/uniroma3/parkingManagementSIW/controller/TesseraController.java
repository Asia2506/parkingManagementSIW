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

import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;
import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.model.TipoOperazione;
import siw.uniroma3.parkingManagementSIW.service.DescrizioneTesseraService;
import siw.uniroma3.parkingManagementSIW.service.DipendenteCCService;
import siw.uniroma3.parkingManagementSIW.service.TesseraService;

@Controller
public class TesseraController {
	
	@Autowired
	TesseraService tesseraService;
	
	@Autowired
	DescrizioneTesseraService tipoTesseraService;
	
	@Autowired
	DipendenteCCService dipendenteCCService;

	
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
	
	@GetMapping("/nuovoTitolareTessera/{numeroTessera}")
	public String newTitolare(@PathVariable("numeroTessera") Long id,Model model) {
		model.addAttribute("tessera",this.tesseraService.getTesseraById(id));
		return "formNewCliente.html";
	}
	 
	
	@PostMapping("/emissioneTessera/associaAnagrafica")
	public String newTessera(@RequestParam("numeroTessera") Long numeroTessera,
			@RequestParam("descrizioneTesseraId") Long descrizioneTesseraId,Model model) {
		//se la tessera non esiste oppure se esiste e non ha un titolare
		Tessera tessera;
		//se la tessera non esiste creala
		if(!this.tesseraService.existsById(numeroTessera))
			tessera=new Tessera(numeroTessera);
		else
			tessera=this.tesseraService.getTesseraById(numeroTessera);
		
		if(tessera.getTitolare()==null && !tessera.isSmarrita() && !tessera.isDanneggiata()) {
			tessera.setDescrizioneTessera(this.tipoTesseraService.getDescrizioneTesseraById(descrizioneTesseraId));
			tessera.setDataEmissione(LocalDate.now());
			if(tessera.getDescrizioneTessera().getTipoTessera().equals("FULL"))
				tessera.setDataScadenza(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
			this.tesseraService.save(tessera);
			model.addAttribute("tessera",tessera);
			return "formAssociaAnagrafica.html";
		}else
			return "redirect:/formNewOperazione/emissione";
		/*if(!this.tesseraService.existsById(tessera.getNumero()) ||
			(this.tesseraService.existsById(tessera.getNumero()) && 
			 this.tesseraService.getTesseraById(tessera.getNumero()).getTitolare()==null && t)) {
			//setta tutti i parametri tranne il titolare
			tessera.setDataEmissione(LocalDate.now());
			tessera.setDescrizioneTessera(this.tipoTesseraService.getDescrizioneTesseraById(descrizioneTesseraId));
			tessera.setDanneggiata(false);
			tessera.setSmarrita(false);
			
			if(tessera.getDescrizioneTessera().getTipoTessera().equals("FULL"))
				tessera.setDataScadenza(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));			//imposta la scadenza all'ultimo giorno del mese
			//salva la tessera nel db 
			this.tesseraService.save(tessera);
			model.addAttribute("tessera",tessera);
			return "formAssociaAnagrafica.html";
			
		}
		model.addAttribute("tipiTessere",this.tipoTesseraService.getAllDescrizioneTessera());
		return "emissioneTessera.html";*/
	}
	
	
	@GetMapping("/emissioneTessera/associaAnagrafica/{numeroTessera}")
	public String scegliTitolareTessera(@PathVariable("numeroTessera") Long id,Model model) {
		
		model.addAttribute("tessera",this.tesseraService.getTesseraById(id));
		model.addAttribute("titolare",null);
		return "formAssociaAnagrafica.html";
			
	}
	
	
	@GetMapping("/emissioneTessera/associaAnagrafica/{numeroTessera}/{idCliente}")
	public String associaTitolareATessera(@PathVariable("numeroTessera") Long numTesssera,@PathVariable("idCliente") Long idTitolare,Model model) {
		
		model.addAttribute("tessera",this.tesseraService.getTesseraById(numTesssera));
		model.addAttribute("titolare",this.dipendenteCCService.getDIpendenteCCById(idTitolare));
		//restituisco la pagina con riepilogo 
		return "formAssociaAnagrafica.html";
	}
	
	
	
	
	@PostMapping("/riepilogoDatiTessera/{tipoOperazione}")
	public String datiTessera(@PathVariable("tipoOperazione") String tipoOperazione,
			@RequestParam("numeroTessera") Long numeroTessera, Model model) {

	    // Simuliamo un servizio per recuperare i dati
	    if (!this.tesseraService.existsById(numeroTessera) ||
	    		(this.tesseraService.existsById(numeroTessera) && this.tesseraService.getTesseraById(numeroTessera).getTitolare()==null)) {
	        // Tessera non trovata
	    	model.addAttribute("error","Numero tessera non valido");
	        return "redirect:/formNewOperazione/"+tipoOperazione; // Pagina di errore o notifica
	    }
	    
	    Tessera tessera = tesseraService.getTesseraById(numeroTessera);
	    DipendenteCC titolare = tessera.getTitolare();
	    // Aggiungo i dati di riepilogo alla vista
	    model.addAttribute("tessera", tessera);
	    model.addAttribute("titolare", titolare);
	    
	    TipoOperazione tipoEnum = TipoOperazione.valueOf(tipoOperazione.toUpperCase());
		
	    switch (tipoEnum) {
	        case SMARRIMENTO:
	            tessera.setSmarrita(true);
	            break;
	        case DANNEGGIAMENTO:
	        	tessera.setDanneggiata(true);
	            break;
	        default:
	        	break;
	    }
	    
	    // Restituisco la pagina con i dati della tessera e del titolare
	    model.addAttribute("tipoOperazione",tipoOperazione);
	    return "riepilogoTessera.html"; 
	}
	
	
	
}
