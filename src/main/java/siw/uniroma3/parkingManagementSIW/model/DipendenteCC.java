package siw.uniroma3.parkingManagementSIW.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class DipendenteCC{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	@NotNull
	private String cognome;
	@NotNull
	private String azienda;
	@NotBlank
	private String targa;
	
	@OneToOne(mappedBy="titolare")
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Tessera getTessera() {
		return tessera;
	}
	public void setTessera(Tessera tessera) {
		this.tessera = tessera;
	}
	public DatiFattura getDatiFatturazione() {
		return datiFatturazione;
	}
	public void setDatiFatturazione(DatiFattura datiFatturazione) {
		this.datiFatturazione = datiFatturazione;
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
