package siw.uniroma3.parkingManagementSIW.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.parkingManagementSIW.model.DescrizioneTessera;
import siw.uniroma3.parkingManagementSIW.repository.DescrizioneTesseraRepository;

@Service
public class DescrizioneTesseraService {
	
<<<<<<< HEAD
	@Autowired
	private DescrizioneTesseraRepository descrizioneTesseraRepository;
	
	
	public DescrizioneTessera getDescrizioneTesseraById(Long id) {
		return descrizioneTesseraRepository.findById(id).get();
	}

	
	public Iterable<DescrizioneTessera> getAllDescrizioneTessera() {
		return descrizioneTesseraRepository.findAll();
	}
	
	
	public void save(DescrizioneTessera descrizioneTessera) {
		descrizioneTesseraRepository.save(descrizioneTessera);	
	}
=======
>>>>>>> 320b77a40f6ef73e09b0c81e1f134a178e22fef5
}
