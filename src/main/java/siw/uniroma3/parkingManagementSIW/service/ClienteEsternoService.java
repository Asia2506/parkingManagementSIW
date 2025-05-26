package siw.uniroma3.parkingManagementSIW.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.parkingManagementSIW.model.ClienteEsterno;
import siw.uniroma3.parkingManagementSIW.repository.ClienteEsternoRepository;

@Service
public class ClienteEsternoService {
	
	@Autowired
	private ClienteEsternoRepository clienteEsternoRepository;
	
	
	public ClienteEsterno getClienteEsternoById(Long id) {
		return clienteEsternoRepository.findById(id).get();
	}

	
	public Iterable<ClienteEsterno> getAllClienteEsterno() {
		return clienteEsternoRepository.findAll();
	}
	
	
	public void save(ClienteEsterno clienteEsterno) {
		clienteEsternoRepository.save(clienteEsterno);	
	}
}
