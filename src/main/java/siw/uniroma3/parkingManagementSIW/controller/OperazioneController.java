package siw.uniroma3.parkingManagementSIW.controller;


import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Iterator;
import java.util.List;

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
	      		model.addAttribute("tipiTessere",this.tipoTesseraService.getAllDescrizioneTessera());
	            return "emissioneTessera.html";
	        /*case RICARICA/RINNOVO, SMARRIMENTO, DANNEGGIAMENTO, RESTITUZIONE:
	         richiedono tutte la ricerca della tessera in quanto devono effettuare
	         l'operazione su una tessera già presente nel sistema */
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
		o.setTipoTessera(t.getDescrizioneTessera());
		
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
	        	o.setTipoOperazione(tipo);
	        	o.setImporto(t.getDescrizioneTessera().getImporto());
	        	//oggi 5giugno
	        	//scadenza perc 31maggio
	        	//se scadenza<oggi allora ultimo giorno mese corrente
	        	
	        	//oggi 25giugno
	        	//scadenza 30giugno
	        	//se oggi<scadenza allora ultimo giorno mese dopo scadenza
	        	if(!t.getDescrizioneTessera().getTipoTessera().equals("SCALARE" )) {
	        		//ultimo giorno del mese successivo a quello dell'ultimo rinnovo
	        		if(LocalDate.now().isAfter(t.getDataScadenza())) {
	        			/*a.isAfter(b) == false
						   a.isAfter(a) == false
						   b.isAfter(a) == true*/
	        			t.setDataScadenza(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
	        		}else {
	        			t.setDataScadenza(t.getDataScadenza().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()));
	        		}
	        	}
	        	
	        	this.tesseraService.save(t);
	        	break;
	        case SMARRIMENTO:
	            o.setTipoOperazione(tipo);
	        	o.setImporto(0);
	        	t.setSmarrita(true);
	        	
	        	t.setTitolare(null);
	    		d.setTessera(null);
	    		this.dipendenteCCService.save(d);
	    		this.tesseraService.save(t);
	    		break;
	        case DANNEGGIAMENTO:
	        	o.setTipoOperazione(tipo);
	        	o.setImporto(0);
	        	t.setDanneggiata(true);
	        	
	        	t.setTitolare(null);
	    		d.setTessera(null);
	    		this.dipendenteCCService.save(d);
	    		this.tesseraService.save(t);
	    		break;
	        case RESTITUZIONE:
	        	//se la tessera che si sta restituendo era stata smarrita settare il parametro a false perchè ritrovata
	        	o.setTipoOperazione(tipo);
	        	o.setImporto(0);
	        	
	        	if(t.isSmarrita()) {
	        		t.setSmarrita(false);
	        	}else {
		        	t.setTitolare(null);
		    		d.setTessera(null);
		    		this.dipendenteCCService.save(d);
	        	}
	        
	    		this.tesseraService.save(t);
	        	break;
	        	
	        default:
	        	
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
		
		return "redirect:/";
	}
	
	
	
	
	
	
	@GetMapping("/operazioniPerPeriodo")
	public String showOperazioniPerPeriodo(Model model) {
		return "cercaOperazioniPerPeriodo.html";		
	 }
	
	
	@PostMapping("/riepilogoOperazioniPerPeriodo")
	public String operazioniPerPeriodo(@RequestParam("dataInizio") LocalDate dataInizio,
			@RequestParam("dataFine") LocalDate dataFine,
			Model model) {

		model.addAttribute("dataInizio",dataInizio);
		model.addAttribute("dataFine",dataFine);
	    model.addAttribute("operazioni", this.operazioneService.getOperazionePerPeriodo(dataInizio, dataFine));
	    return "operazioniPerPeriodo.html"; 
	}
	
	
	
	@GetMapping("/cancellaOperazione")
	public String operazioneCancellazione(Model model) {
		model.addAttribute("cancella",true);
		model.addAttribute("operazioniOggi",this.operazioneService.getAllOperazioniDiOggi());
		return "home.html";	
	}
	
	
	@GetMapping("/effettuaCancellazione/{idOperazione}")
	public String effettuaCancellazione(@PathVariable("idOperazione") Long id,Model model) {
		Operazione o=this.operazioneService.getOperazioneById(id);
		Tessera t=o.getTessera();
		t.getOperazioni().remove(o);
		
		DipendenteCC c=o.getCliente();
		
		switch(o.getTipoOperazione()) {
		
			case RICARICA:
				if(o.getTipoTessera().getTipoTessera()=="FULL" || o.getTipoTessera().getTipoTessera()=="FERIALE")
					t.setDataScadenza(t.getDataScadenza().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth())); 
				break;
			case EMISSIONE:
				//scollego la tessera dal titolare e viceversa
				c.setTessera(null);
				t.setTitolare(null);
				break;
			case DANNEGGIAMENTO:
				t.setDanneggiata(false);
				c.setTessera(t);
				t.setTitolare(c);
				break;
			case RESTITUZIONE:
				//ricollego la tessera al vecchio titolare e viceversa
				t.setTitolare(c);
				c.setTessera(t);
				break;
			case SMARRIMENTO:
				t.setSmarrita(false);
				c.setTessera(t);
				t.setTitolare(c);
				break;
			default:
				break;
		}
		
		
		this.dipendenteCCService.save(c);
		this.tesseraService.save(t);
		this.operazioneService.delete(o);
		
		return "redirect:/";
	}
		

}
