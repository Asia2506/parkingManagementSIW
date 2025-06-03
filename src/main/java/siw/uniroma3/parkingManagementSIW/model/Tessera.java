package siw.uniroma3.parkingManagementSIW.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Tessera {
	
	@Id
	private Long numero;
	private LocalDate dataEmissione;
	private LocalDate dataScadenza;
	private boolean danneggiata;
	private boolean smarrita;
	private boolean restituita;
	@ManyToOne
	private DescrizioneTessera descrizioneTessera;
	@OneToOne
	private DipendenteCC titolare;
	@OneToMany(mappedBy ="tessera")
	private List<Operazione> operazioni;

	public Tessera() {
		this.restituita = false;
		this.danneggiata = false;
		this.smarrita = false;
	}
	
	
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
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
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
	public DescrizioneTessera getDescrizioneTessera() {
		return descrizioneTessera;
	}
	public void setDescrizioneTessera(DescrizioneTessera descrizioneTessera) {
		this.descrizioneTessera = descrizioneTessera;
	}
	public DipendenteCC getTitolare() {
		return titolare;
	}
	public void setTitolare(DipendenteCC titolare) {
		this.titolare = titolare;
	}
	public List<Operazione> getOperazioni() {
		return operazioni;
	}
	public void setOperazioni(List<Operazione> operazioni) {
		this.operazioni = operazioni;
	}
	public boolean isDanneggiata() {
		return danneggiata;
	}
	public void setDanneggiata(boolean danneggiata) {
		this.danneggiata = danneggiata;
	}
	public boolean isSmarrita() {
		return smarrita;
	}
	public void setSmarrita(boolean smarrita) {
		this.smarrita = smarrita;
	}
	public boolean isRestituita() {
		return restituita;
	}
	public void setRestituita(boolean restituita) {
		this.restituita = restituita;
	}
	
	
	
	
}
