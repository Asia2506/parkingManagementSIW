package siw.uniroma3.parkingManagementSIW.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.parkingManagementSIW.model.DescrizioneTessera;
import siw.uniroma3.parkingManagementSIW.repository.DescrizioneTesseraRepository;

@Service
public class DescrizioneTesseraService {
	
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
}
