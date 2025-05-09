package siw.uniroma3.parkingManagementSIW.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
