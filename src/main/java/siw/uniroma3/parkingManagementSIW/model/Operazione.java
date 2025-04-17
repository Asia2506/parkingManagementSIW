package siw.uniroma3.parkingManagementSIW.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Operazione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private LocalDate data;
	//private tipologiaOperazione tipo;
	private String tipoPagamento;
	
	
	
	/*----METODI EQUALS AND HASHCODE----*/
	@Override
	public int hashCode() {
		return Objects.hash(data, tipoPagamento);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operazione other = (Operazione) obj;
		return Objects.equals(data, other.data) && Objects.equals(tipoPagamento, other.tipoPagamento);
	}
	
	
	/*----METODI GETTERS AND SETTERS----*/
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	
	
	
	
}
