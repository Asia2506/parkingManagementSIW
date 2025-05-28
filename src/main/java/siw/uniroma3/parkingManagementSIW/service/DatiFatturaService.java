package siw.uniroma3.parkingManagementSIW.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.parkingManagementSIW.model.DatiFattura;
import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;
import siw.uniroma3.parkingManagementSIW.repository.DatiFatturaRepository;

@Service
public class DatiFatturaService {

	@Autowired
	private DatiFatturaRepository datiFatturaRepository;
	
	
	public DatiFattura getDatiFatturaById(Long id) {
		return datiFatturaRepository.findById(id).get();
	}

	
	public Iterable<DatiFattura> getAllDatiFattura() {
		return datiFatturaRepository.findAll();
	}
	
	
	public void save(DatiFattura datiFattura) {
		datiFatturaRepository.save(datiFattura);	
	}
	
	public Iterable<DatiFattura> getAllDatiFatturaByRagioneSociale(String ragioneSociale){
		return this.datiFatturaRepository.findByRagioneSociale(ragioneSociale);
	}
}
