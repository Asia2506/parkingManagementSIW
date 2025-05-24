package siw.uniroma3.parkingManagementSIW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.model.TipoOperazione;
import siw.uniroma3.parkingManagementSIW.service.DescrizioneTesseraService;
import siw.uniroma3.parkingManagementSIW.service.OperazioneService;

@Controller
public class OperazioneController {
	
	@Autowired
	OperazioneService operazioneService;
	@Autowired
	DescrizioneTesseraService	tipoTesseraService;
	
	@GetMapping("/formNewOperazione/{tipoOperazione}")
	public String formNewOperazione(@PathVariable("tipoOperazione") String tipo,Model model) {
		
		model.addAttribute("tipoOperazione", tipo);
		
		TipoOperazione tipoEnum = TipoOperazione.valueOf(tipo.toUpperCase());

        switch (tipoEnum) {
            case EMISSIONE:
	      		  model.addAttribute("tessera", new Tessera());
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
	

}
