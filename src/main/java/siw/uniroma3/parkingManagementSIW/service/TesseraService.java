package siw.uniroma3.parkingManagementSIW.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.parkingManagementSIW.model.Operazione;
import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.model.TipoOperazione;
import siw.uniroma3.parkingManagementSIW.repository.TesseraRepository;

@Service
public class TesseraService {
	
	@Autowired
	private TesseraRepository tesseraRepository;
	
	public Tessera getTesseraById(Long numero) {
		return tesseraRepository.findById(numero).get();
	}
	
	
	public Iterable<Tessera> getAllTessereAttive() {
		return this.tesseraRepository.findTessereAttive();
	}

	public void save(Tessera tessera) {
		tesseraRepository.save(tessera);
	}
	
	public void deleteById(Long numero) {
		tesseraRepository.deleteById(numero);
	}
	
	public boolean existsById(Long id) {
        return tesseraRepository.existsById(id);
    }
	
	public boolean findTesseraById(Long numero) {
		return tesseraRepository.existsById(numero);
	}
	
	
	/* Restituisce l'ultima operazione di smarrimento per una data tessera */
    /*public Operazione getUltimaOperazioneSmarrimento(Long numeroTessera) {
        List<Operazione> operazioniSmarrimento = tesseraRepository.findLastOperationByTesseraAndTipo(numeroTessera, TipoOperazione.SMARRIMENTO);
        if (!operazioniSmarrimento.isEmpty()) {
            return operazioniSmarrimento.get(0);
        }
        return null;
    }*/

}
