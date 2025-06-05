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
import siw.uniroma3.parkingManagementSIW.repository.OpreazioneRepository;
import siw.uniroma3.parkingManagementSIW.service.DescrizioneTesseraService;
import siw.uniroma3.parkingManagementSIW.service.DipendenteCCService;
import siw.uniroma3.parkingManagementSIW.service.OperazioneService;
import siw.uniroma3.parkingManagementSIW.service.TesseraService;

@Controller
public class TesseraController {

    private final OpreazioneRepository opreazioneRepository;
	
	@Autowired
	TesseraService tesseraService;
	
	@Autowired
	DescrizioneTesseraService tipoTesseraService;
	
	@Autowired
	DipendenteCCService dipendenteCCService;
	


    TesseraController(OpreazioneRepository opreazioneRepository) {
        this.opreazioneRepository = opreazioneRepository;
    }

	
	@GetMapping("/tessera/{id}")
	public String getTessera(@PathVariable("id") Long id, Model model) {		 
		model.addAttribute("tessera", this.tesseraService.getTesseraById(id));
		return "tessera.html";	
	}

	
	@GetMapping("/tessereAttive")
	public String showTessereEmesse(Model model) {
		model.addAttribute("tessereAttive", this.tesseraService.getAllTessere());
		return "tessereAttive.html";		
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
	    		(this.tesseraService.existsById(numeroTessera) && this.tesseraService.getTesseraById(numeroTessera).getTitolare()==null &&
	    				!this.tesseraService.getTesseraById(numeroTessera).isSmarrita())) {
	        // Tessera non trovata
	    	model.addAttribute("error","Numero tessera non valido");
	        return "redirect:/formNewOperazione/"+tipoOperazione; // Pagina di errore o notifica
	    }
	    
	    Tessera tessera = tesseraService.getTesseraById(numeroTessera);
	    DipendenteCC titolare = tessera.getTitolare();
	    // Aggiungo i dati di riepilogo alla vista
	    
	    
	    TipoOperazione tipoEnum = TipoOperazione.valueOf(tipoOperazione.toUpperCase());
		
	    switch (tipoEnum) {
	        case SMARRIMENTO:
	            tessera.setSmarrita(true);
	            break;
	        case DANNEGGIAMENTO:
	        	tessera.setDanneggiata(true);
	            break;
	        case RESTITUZIONE:
	        	if(tessera.isSmarrita()) {
	        		titolare=this.tesseraService.getUltimaOperazioneSmarrimento(numeroTessera).getCliente();
	        	}
	        default:
	        	break;
	    }
	    
	    model.addAttribute("tessera", tessera);
	    model.addAttribute("titolare", titolare);
	    // Restituisco la pagina con i dati della tessera e del titolare
	    model.addAttribute("tipoOperazione",tipoOperazione);
	    return "riepilogoTessera.html"; 
	}
	
	
	
}
