package siw.uniroma3.parkingManagementSIW.model;

import jakarta.persistence.OneToOne;

public class Cliente {
	
	@OneToOne
	private Tessera tessera;
	
}
