package siw.uniroma3.parkingManagementSIW.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class DipendenteCC extends Cliente{
	
	private String nome;
	private String cognome;
	private String azienda;
	private String targa;
	
	@OneToOne
	private Tessera tessera;
	@ManyToOne
	private DatiFattura datiFatturazione;
	
	
	
	/*----METODI EUQALS AND HASHCODE----*/
	@Override
	public int hashCode() {
		return Objects.hash(azienda, cognome, nome, targa);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DipendenteCC other = (DipendenteCC) obj;
		return Objects.equals(azienda, other.azienda) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(nome, other.nome) && Objects.equals(targa, other.targa);
	}
	
	
	
	/*----METODI GETTERS AND SETTERS----*/
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getAzienda() {
		return azienda;
	}
	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}
	
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	
	
		
}
