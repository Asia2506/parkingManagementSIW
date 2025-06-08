package siw.uniroma3.parkingManagementSIW.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;
import siw.uniroma3.parkingManagementSIW.model.Operazione;
import siw.uniroma3.parkingManagementSIW.model.TipoOperazione;
import siw.uniroma3.parkingManagementSIW.repository.OpreazioneRepository;

@Service
public class OperazioneService {
	
	@Autowired 
	private OpreazioneRepository operazioneRepository;
	
	
	public Iterable<Operazione> getAllOperazioni() {
		return operazioneRepository.findAll();
	}
	
	
	
	/*Restituisce tutte le operazioni effettuate in data odierna*/
	public Iterable<Operazione> getAllOperazioniDiOggi() {
		LocalDate dataDiOggi = LocalDate.now();
		
		/*List<Operazione> operazioniOggi = new ArrayList<>();
		
		for(Operazione operazione: this.getAllOperazioni()) {
			if(operazione.getData()==dataDiOggi)
				operazioniOggi.add(operazione);
		}
		
		return operazioniOggi;*/
		
		return operazioneRepository.findByData(dataDiOggi);
	}
	
	public Iterable<Operazione> getAllOperazioniPerData(int giorno, int mese, int anno){
		LocalDate data = LocalDate.of(anno, mese, giorno);
		return operazioneRepository.findByData(data);
	}
	
	
	public void save(Operazione operazione) {
		this.operazioneRepository.save(operazione);
	}
	
	public Operazione getOperazioneById(Long id) {
		return this.operazioneRepository.findById(id).get();
	}
	
	
	/* Restituisce il cliente dell'ultima operazione di smarrimento per una data tessera */
	/*public DipendenteCC getClienteUltimoSmarrimento(Long numeroTessera) {
		List<Operazione> operazioni = this.operazioneRepository.findLastSmarrimentoOperationByTessera(numeroTessera, TipoOperazione.SMARRIMENTO);
		if (!operazioni.isEmpty()) {
			return operazioni.get(0).getCliente();
		}
		return null;
	}*/


}
