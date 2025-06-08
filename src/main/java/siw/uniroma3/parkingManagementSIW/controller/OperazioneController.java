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
	        	//model.addAttribute("tessera",new Tessera());
	      		model.addAttribute("tipiTessere",this.tipoTesseraService.getAllDescrizioneTessera());
	            return "emissioneTessera.html";
	        /*case SMARRIMENTO:
	            return "smarrimentoTessera.html";
	        case DANNEGGIAMENTO:
	            return "danneggiamentoTessera.html";
	        case RESTITUZIONE:
	        	model.addAttribute("tipoOperazione",tipo);
	            return "restituzioneTessera.html";*/
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
	        	if(t.getDescrizioneTessera().getTipoTessera()!="SCALARE" ) {
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
		
		//model.addAttribute("operazioniOggi",this.operazioneService.getAllOperazioniDiOggi());
		//return "home.html";
		return "redirect:/";
	}
	
	@GetMapping("/operazioniPerTessera")
	public String showOperazioniPerTessera(Model model) {
		return "cercaTesseraPerVisualizzazioneDati.html";		
	 }

	@PostMapping("/riepilogoOperazioniTessera")
	public String datiTessera(@RequestParam("numeroTessera") Long numeroTessera, Model model) {

	    // Simuliamo un servizio per recuperare i dati
	    if (!this.tesseraService.existsById(numeroTessera) ||
	    		(this.tesseraService.existsById(numeroTessera) && this.tesseraService.getTesseraById(numeroTessera).getTitolare()==null &&
	    				!this.tesseraService.getTesseraById(numeroTessera).isSmarrita())) {
	        // Tessera non trovata
	    	model.addAttribute("error","Numero tessera non valido");
	    	return "cercaTesseraPerVisualizzazioneDati.html";
	       // return "redirect:/operazioniPerTessera"; // Pagina di errore o notifica
	    }
	    
	    Tessera tessera = tesseraService.getTesseraById(numeroTessera);
	    DipendenteCC titolare = tessera.getTitolare();
	    List<Operazione> operazioniTessera = tessera.getOperazioni();
	    Iterator<Operazione> iterator = operazioniTessera.iterator();
	    
	    // Prendo le operazioni associate a quel titolare
	    while (iterator.hasNext()) {
	        Operazione operazione = iterator.next();
	        if (!operazione.getCliente().equals(titolare)) {
	            iterator.remove();
	        }
	    }
	    
	    // Aggiungo i dati di riepilogo alla vista
	    model.addAttribute("tessera", tessera);
	    model.addAttribute("titolare", titolare);
	    model.addAttribute("operazioniTessera", operazioniTessera);
	    return "riepilogoOperazioniTessera.html"; 
	}
	
	
	@GetMapping("/operazioniPerPeriodo")
	public String showOperazioniPerPeriodo(Model model) {
		return "cercaOperazioniPerData.html";		
	 }
	@PostMapping("/riepilogoOperazioniPerData")
	public String operazioniPerData(@RequestParam("giorno") int giorno, @RequestParam("mese") int mese, @RequestParam("anno") int anno, Model model) {

	    // Simuliamo un servizio per recuperare i dati
	   
	    Iterable<Operazione> operazioniPerGiorno = this.operazioneService.getAllOperazioniPerData(giorno, mese, anno);
//	    Iterator<Operazione> iterator = operazioniPerGiorno.iterator();
//	    
//	    // Prendo le operazioni associate a quel titolare
//	    while (iterator.hasNext()) {
//	        Operazione operazione = iterator.next();
//	        if (!operazione.getCliente().equals(titolare)) {
//	            iterator.remove();
//	        }
//	    }
	    
	    // Aggiungo i dati di riepilogo alla vista
	    model.addAttribute("operazioniPerGiorno", operazioniPerGiorno);
	    return "operazioniPerGiorno.html"; 
	}
		

}
