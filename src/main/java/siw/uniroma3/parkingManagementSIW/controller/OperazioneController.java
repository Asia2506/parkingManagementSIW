package siw.uniroma3.parkingManagementSIW.controller;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;
import siw.uniroma3.parkingManagementSIW.model.Operazione;
import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.model.TipoOperazione;
import siw.uniroma3.parkingManagementSIW.service.DescrizioneTesseraService;
import siw.uniroma3.parkingManagementSIW.service.DipendenteCCService;
import siw.uniroma3.parkingManagementSIW.service.OperazioneService;
import siw.uniroma3.parkingManagementSIW.service.TesseraService;

@Controller
public class OperazioneController {
	
	@Autowired
	OperazioneService operazioneService;
	@Autowired
	DescrizioneTesseraService	tipoTesseraService;
	@Autowired
	TesseraService tesseraService;
	@Autowired
	DipendenteCCService dipendenteCCService;
	
	
	
	@GetMapping("/formNewOperazione/{tipoOperazione}")
	public String formNewOperazione(@PathVariable("tipoOperazione") String tipo,Model model) {
	
		TipoOperazione tipoEnum = TipoOperazione.valueOf(tipo.toUpperCase());
		
	
	    switch (tipoEnum) {
	        case EMISSIONE:
	        	model.addAttribute("tessera",new Tessera());
	      		model.addAttribute("tipiTessere",this.tipoTesseraService.getAllDescrizioneTessera());
	            return "emissioneTessera.html";
	        case SMARRIMENTO:
	            return "smarrimentoTessera.html";
	        case DANNEGGIAMENTO:
	            return "danneggiamentoTessera.html";
	        case RESTITUZIONE:
	            return "restituzioneTessera.html";
	        default:
	        	return "ricaricaTessera.html";
	        }
			
	}
	

	
	
	
	@PostMapping("/salvaOperazione/{tipoOperazione}/{numeroTessera}/{idTitolare}")
	public String salvaOperazione(@PathVariable("tipoOperazione") String tipo,@PathVariable("numeroTessera") Long numTessera,
			@PathVariable("idTitolare") Long idTitolare,Model model) {
		
		//cerco la tessera nedl db
		Tessera t=this.tesseraService.getTesseraById(numTessera);
		//cerco il cliente/dipendenteCC (titolare) nel db
		DipendenteCC d=this.dipendenteCCService.getDIpendenteCCById(idTitolare);
		
		//creo e setto parametri comuni per le operazioni
		Operazione o=new Operazione();
		o.setData(LocalDate.now());
		o.setCliente(d);
		o.setTessera(t);
		
		//aggiungo alle operazioni associate alla tessera quella corrente
		t.getOperazioni().add(o);
		
		TipoOperazione tipoEnum = TipoOperazione.valueOf(tipo.toUpperCase());
	    switch (tipoEnum) {
	        case EMISSIONE:
	        	o.setTipoOperazione(tipo);
	        	
	        	//associo la tessera con il titolare e viceversa
	    		t.setTitolare(d);
	    		d.setTessera(t);
	    		
	    		//salvo nel db le modifiche effettuate
	    		this.dipendenteCCService.save(d);
	    		this.tesseraService.save(t);
	        	
	        case SMARRIMENTO:
	            
	        case DANNEGGIAMENTO:
	        	
	        case RESTITUZIONE:
	        	o.setTipoOperazione(tipo);
	        	t.setTitolare(null);
	    		d.setTessera(null);
	    		this.dipendenteCCService.save(d);
	    		this.tesseraService.save(t);
	    		
	        	
	        default:
	        	
	       }
	    
	    this.operazioneService.save(o);
	    model.addAttribute("operazione",o);
	    return "riepilogoOperazione.html";
		
	}
	
	
	@PostMapping("/confermaOperazione/{idOperazione}")
    public String confermaPagamento(@PathVariable("idOperazione") Long id,
    		@RequestParam("tipoPagamento") String tipoPagamento, Model model) {
		
		Operazione o=this.operazioneService.getOperazioneById(id);
		o.setTipoPagamento(tipoPagamento);
		this.operazioneService.save(o);
		
		model.addAttribute("operazioniOggi",this.operazioneService.getAllOperazioniDiOggi());
		return "home.html";
	}
		

}
