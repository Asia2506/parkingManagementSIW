package siw.uniroma3.parkingManagementSIW.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.parkingManagementSIW.model.Utente;
import siw.uniroma3.parkingManagementSIW.repository.UtenteRepository;

@Service
public class UtenteService {
	
	
	@Autowired 
	private UtenteRepository utenteRepository;
	
	public Utente getUtenteById(String username) {
	return utenteRepository.findById(username).get();
	}
	
	public Iterable<Utente> getAllUtenti() {
	return utenteRepository.findAll();
	}



}
