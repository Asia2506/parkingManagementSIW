package siw.uniroma3.parkingManagementSIW.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.parkingManagementSIW.model.Operazione;
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
	
	
	public void save(Operazione operazione) {
		this.operazioneRepository.save(operazione);
	}


}
