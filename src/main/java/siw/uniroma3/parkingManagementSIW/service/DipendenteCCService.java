package siw.uniroma3.parkingManagementSIW.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;
import siw.uniroma3.parkingManagementSIW.repository.DipendenteCCRepository;

@Service
public class DipendenteCCService {

	@Autowired
	private DipendenteCCRepository dipendenteCCRepository;
	
	
	public DipendenteCC getDIpendenteCCById(Long id) {
		return dipendenteCCRepository.findById(id).get();
	}

	public Iterable<DipendenteCC> getAllDipendenti() {
		return dipendenteCCRepository.findAll();
	}

	
	public void save(DipendenteCC dipendenteCC) {
		dipendenteCCRepository.save(dipendenteCC);
	}
	
	
	public void deleteById(Long id) {
		dipendenteCCRepository.deleteById(id);
	}
	
	
	public Iterable<DipendenteCC> getAllDipendentiBy(String nome,String cognome){
		return dipendenteCCRepository.findByNomeAndCognome(nome, cognome);
	}

	public boolean existsByNomeAndCognome(String nome, String cognome) {
		
		List<DipendenteCC> tutti = dipendenteCCRepository.findByNomeAndCognome(nome, cognome);
		for (DipendenteCC dipendente : tutti) {
	        if (dipendente.getNome().equalsIgnoreCase(nome) && dipendente.getCognome().equalsIgnoreCase(cognome)) {
	            return true;
	        }
	    }
	    return false;
	}


}
