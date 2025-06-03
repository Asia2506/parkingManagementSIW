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
	DescrizioneTesseraService tipoTesseraService;
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
	        /*case SMARRIMENTO:
	            return "smarrimentoTessera.html";
	        case DANNEGGIAMENTO:
	            return "danneggiamentoTessera.html";
	        case RESTITUZIONE:
	        	model.addAttribute("tipoOperazione",tipo);
	            return "restituzioneTessera.html";*/
	        case RICARICA:
	        	return "ricaricaTessera.html";
	        default:
	        	return "cercaTesseraPerOperazione.html";
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
	        	o.setImporto(t.getDescrizioneTessera().getImporto());
	        	//associo la tessera con il titolare e viceversa
	    		t.setTitolare(d);
	    		d.setTessera(t);
	    		
	    		//salvo nel db le modifiche effettuate
	    		this.dipendenteCCService.save(d);
	    		this.tesseraService.save(t);
	        	break;
	        case RICARICA:
	        	break;
	        case SMARRIMENTO:
	            t.setSmarrita(true);
	        case DANNEGGIAMENTO:
	        	t.setDanneggiata(true);
	        case RESTITUZIONE:
	        	//se la tessera è smarrita si può restituire	
	        	//devo cercare l'operazione di smarrimento per risalire all'ultimo titolare
	        	
	        default:
	        	o.setTipoOperazione(tipo);
	        	o.setImporto(0);
	        	
	        	t.setTitolare(null);
	    		d.setTessera(null);
	    		this.dipendenteCCService.save(d);
	    		this.tesseraService.save(t);
	        	break;
	    }
	    
	    this.operazioneService.save(o);
	    t.getOperazioni().add(o);
	    //model.addAttribute("operazione",o);
	    //return "riepilogoOperazione.html";
	    if (tipoEnum == TipoOperazione.EMISSIONE || tipoEnum == TipoOperazione.RICARICA) {
	        // Per EMISSIONE o RICARICA, vai alla pagina di riepilogo con selezione pagamento
	        model.addAttribute("operazione", o);
	        return "riepilogoOperazione.html";
	    } else {
	        // Per SMARRIMENTO, DANNEGGIAMENTO, RESTITUZIONE, vai direttamente alla home
	        // e carica le operazioni di oggi direttamente qui
	        //model.addAttribute("operazioniOggi", this.operazioneService.getAllOperazioniDiOggi());
	        //return "home.html";
	    	return "redirect:/";
	    }
		
	}
	
	
	@PostMapping("/confermaOperazione/{idOperazione}")
    public String confermaPagamento(@PathVariable("idOperazione") Long id,
    		@RequestParam("tipoPagamento") String tipoPagamento, Model model) {
		
		Operazione o=this.operazioneService.getOperazioneById(id);
		if(tipoPagamento!=null)
			o.setTipoPagamento(tipoPagamento);
		this.operazioneService.save(o);
		
		//model.addAttribute("operazioniOggi",this.operazioneService.getAllOperazioniDiOggi());
		//return "home.html";
		return "redirect:/";
	}
		

}
