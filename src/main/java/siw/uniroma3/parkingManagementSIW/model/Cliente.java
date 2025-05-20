package siw.uniroma3.parkingManagementSIW.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	
}
