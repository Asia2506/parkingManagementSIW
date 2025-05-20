package siw.uniroma3.parkingManagementSIW.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.repository.TesseraRepository;

@Service
public class TesseraService {
	
	@Autowired
	private TesseraRepository tesseraRepository;
	
	public Tessera getTesseraById(Long numero) {
	return tesseraRepository.findById(numero).get();
	}
	
	public Iterable<Tessera> getAllTessere() {
	return tesseraRepository.findAll();
	}

	public void save(Tessera tessera) {
		tesseraRepository.save(tessera);
	}
	
	public void deleteById(Long numero) {
		tesseraRepository.deleteById(numero);
	}

}
