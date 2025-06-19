package siw.uniroma3.parkingManagementSIW.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
		
		return this.operazioneRepository.findByData(dataDiOggi);
	}
	
	
	
	public List<Operazione> getOperazionePerPeriodo(LocalDate dataInizio,LocalDate dataFine){
		return this.operazioneRepository.findByPeriodo(dataInizio, dataFine);
	}
	
	
	public void save(Operazione operazione) {
		this.operazioneRepository.save(operazione);
	}
	
	
	public void delete(Operazione operazione) {
		this.operazioneRepository.delete(operazione);
	}
	
	
	public Operazione getOperazioneById(Long id) {
		return this.operazioneRepository.findById(id).get();
	}
	
	
	
	
	/* Restituisce l'ultima operazione di smarrimento per una data tessera */
    public Operazione getUltimaOperazioneSmarrimento(Long numeroTessera) {
        Optional<Operazione> optionalOperazione = operazioneRepository.findSmarrimentoOperationByTessera(numeroTessera, TipoOperazione.SMARRIMENTO);
        return optionalOperazione.orElse(null);
    }
    
    
    public List<Operazione> getOperazioniCorrenti(Long numeroTessera){
    	return this.operazioneRepository.findOperazioniByTesseraAndTitolareCorrente(numeroTessera);
    }
    
    
    
    


}
