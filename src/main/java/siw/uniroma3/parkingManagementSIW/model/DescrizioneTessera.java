package siw.uniroma3.parkingManagementSIW.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DescrizioneTessera {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String tipoTessera;
	private String descrizione;
	private float importo;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTipoTessera() {
		return tipoTessera;
	}
	public void setTipoTessera(String tipoTessera) {
		this.tipoTessera = tipoTessera;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public float getImporto() {
		return importo;
	}
	public void setImporto(float importo) {
		this.importo = importo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(descrizione, importo, tipoTessera);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DescrizioneTessera other = (DescrizioneTessera) obj;
		return Objects.equals(descrizione, other.descrizione)
				&& Float.floatToIntBits(importo) == Float.floatToIntBits(other.importo)
				&& Objects.equals(tipoTessera, other.tipoTessera);
	}
	

}
