package siw.uniroma3.parkingManagementSIW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
public class DipendenteCCController {
	
	@Autowired
	DipendenteCCService dipendenteCCService;
	
	@Autowired
	TesseraService tesseraService;
	
	@Autowired
	DatiFatturaService datiFatturaService;
	
	
	
	
	@GetMapping("/ricercaCliente/{numeroTessera}")
	private String findCliente(@PathVariable("numeroTessera") Long id, // PathVariable for tessera number
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cognome", required = false) String cognome,
			Model model) {
		
		model.addAttribute("clientiTrovati", this.dipendenteCCService.getAllDipendentiBy(nome, cognome));
		model.addAttribute("tessera", this.tesseraService.getTesseraById(id));
		return "formRicercaTitolareTessera.html";
	}
	
	@PostMapping("/creaNuovoCliente/{numeroTessera}")
	private String newCliente(@PathVariable("numeroTessera") Long id, // PathVariable for tessera number
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cognome", required = false) String cognome,
			@RequestParam(value = "azienda", required = false) String azienda,
			@RequestParam(value = "targa", required = false) String targa,
			@RequestParam(value = "hasDatiFattura", required = false) boolean hasDatiFattura,
			Model model) {
		
		DipendenteCC d=new DipendenteCC();
		d.setNome(nome);
		d.setCognome(cognome);
		d.setAzienda(azienda);
		d.setTarga(targa);
		
		this.dipendenteCCService.save(d);
	
		Tessera t=this.tesseraService.getTesseraById(id);
		
		if(hasDatiFattura) {
			model.addAttribute("cliente",d);
			model.addAttribute("tessera",t);
			return "formDatiFatturazione.html";
		}
		
		return "redirect:/emissioneTessera/associaAnagrafica/"+t.getNumero()+"/"+d.getId();
	}
	
	
	
	@GetMapping("/emissioneTessera/creaNuovoCliente/associaDatiFatturazione/{numeroTessera}/{idCliente}/{idDatiFatturazione}")
	private String saveDatiFatturazione(@PathVariable("numeroTessera") Long numTessera,
			@PathVariable("idCliente") Long idCliente,
			@PathVariable("idDatiFatturazione") Long idDF,Model model) {
		
		DipendenteCC d=this.dipendenteCCService.getDIpendenteCCById(idCliente);
		DatiFattura df=this.datiFatturaService.getDatiFatturaById(idDF);
		
		d.setDatiFatturazione(df);
		
		this.dipendenteCCService.save(d);
		
		Tessera t=this.tesseraService.getTesseraById(numTessera);		
		
		return "redirect:/emissioneTessera/associaAnagrafica/"+t.getNumero()+"/"+d.getId();
	}
	
	
	
	
	
}
