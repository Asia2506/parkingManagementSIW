package siw.uniroma3.parkingManagementSIW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		model.addAttribute("cliente",this.dipendenteCCService.getDIpendenteCCById(id));
		model.addAttribute(this.tesseraService.getTesseraById(numeroTessera));
		model.addAttribute("datiFatturazione",this.datiFatturaService.getAllDatiFatturaByRagioneSociale(ragioneSociale));
		return "formDatiFatturazione.html";
	}
	
	
	@PostMapping("/creaNuoviDatiFatturazione/{numeroTessera}/{idCliente}")
	private String newDatiFattura(@PathVariable("numeroTessera") Long id,
			@PathVariable("idCliente") Long idCliente,
			@ModelAttribute("datiFatturazione") DatiFattura datiFattura,
			/*@RequestParam(value = "ragioneSociale", required = false) String ragioneSociale,
			@RequestParam(value = "partitaIva", required = false) String partitaIva,
			@RequestParam(value = "pec", required = false) String pec,
			@RequestParam(value = "codiceUnivoco", required = false) String codiceUnivoco,
			@RequestParam(value = "indirizzo", required = false) String indirizzo,*/
			Model model) {
		
		DipendenteCC d=this.dipendenteCCService.getDIpendenteCCById(idCliente);
		Tessera t=this.tesseraService.getTesseraById(id);
		
		/*DatiFattura df=new DatiFattura();
		df.setRagioneSociale(ragioneSociale);
		df.setCodiceUnivoco(codiceUnivoco);
		df.setCodiceUnivoco(indirizzo);
		df.setPec(pec);
		df.setPartitaIva(partitaIva);*/
		
		this.datiFatturaService.save(datiFattura);
		
		return "redirect:/emissioneTessera/creaNuovoCliente/associaDatiFatturazione/"+t.getNumero()+"/"+d.getId()+"/"+datiFattura.getId();
	}

}
