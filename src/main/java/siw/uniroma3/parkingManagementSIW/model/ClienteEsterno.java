package siw.uniroma3.parkingManagementSIW.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class ClienteEsterno extends Cliente{
	
	@Id
	private String nome;
	
	@OneToOne
	private DatiFattura datiFatturazione;
	@OneToMany
	private List<Tessera> tessereDipendenti;
	
	
	
	/*----METODI EUQALS AND HASHCODE----*/
	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteEsterno other = (ClienteEsterno) obj;
		return Objects.equals(nome, other.nome);
	}
	
	
	
	/*----METODI GETTERS AND SETTERS----*/
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
