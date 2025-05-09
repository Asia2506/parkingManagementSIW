package siw.uniroma3.parkingManagementSIW.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Tessera {
	
	@Id
	private int numero;
	private LocalDate dataEmissione;
	private LocalDate dataScadenza;
	
	@OneToOne
	private Cliente titolare;

	
	
	
	/*----METODI EQUALS AND HASHCODE----*/
	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tessera other = (Tessera) obj;
		return numero == other.numero;
	}
	
	
	
	/*----METODI GETTERS AND SETTERS----*/
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public LocalDate getDataEmissione() {
		return dataEmissione;
	}
	public void setDataEmissione(LocalDate dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	
	public LocalDate getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(LocalDate dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	
}
