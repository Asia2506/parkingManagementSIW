package siw.uniroma3.parkingManagementSIW.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DatiFattura {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    @Size(min = 6, max = 7)
    private String codiceUnivoco;
    
    @NotBlank
    @Email
    private String pec;
    
    @NotBlank
    private String partitaIva;
    
    @NotBlank
    private String ragioneSociale;
    
    @NotBlank
    private String indirizzo;
 
    /*----METODI EQUALS AND HASHCODE----*/
    @Override
	public int hashCode() {
		return Objects.hash(codiceUnivoco, indirizzo, partitaIva, pec, ragioneSociale);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatiFattura other = (DatiFattura) obj;
		return Objects.equals(codiceUnivoco, other.codiceUnivoco) && Objects.equals(indirizzo, other.indirizzo)
				&& Objects.equals(partitaIva, other.partitaIva) && Objects.equals(pec, other.pec)
				&& Objects.equals(ragioneSociale, other.ragioneSociale);
	}
	
	
	
	
	/*----METODI GETTERS AND SETTERS----*/
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }
    public void setCodiceUnivoco(String codiceUnivoco) {
        this.codiceUnivoco = codiceUnivoco;
    }

    public String getPec() {
        return pec;
    }
    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getPartitaIva() {
        return partitaIva;
    }
    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    
}
