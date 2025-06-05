package siw.uniroma3.parkingManagementSIW.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Operazione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private LocalDate data;
	private TipoOperazione tipoOperazione;
	private String tipoPagamento;
	private float cauzione;
	private float importo;
	@ManyToOne
	private Tessera tessera;
	//per non perdere i dati delle operazioni
	@ManyToOne
	private DipendenteCC cliente;
	
	
	

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
	public float getCauzione() {
		return cauzione;
	}
	public void setCauzione(float cauzione) {
		this.cauzione = cauzione;
	}
	
	public float getImporto() {
		return importo;
	}
	public void setImporto(float importo) {
		this.importo = importo;
	}
	
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public TipoOperazione getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = TipoOperazione.valueOf(tipoOperazione.toUpperCase());
		switch (this.tipoOperazione) {
			case RESTITUZIONE:
				this.cauzione = -10;
				break;
			case EMISSIONE:
				this.cauzione = 10;
				break;
			default:  // RICARICA, SMARRIMENTO, DANNEGGIAMENTO
				this.cauzione = 0;
		}
		
	}
	public Tessera getTessera() {
		return tessera;
	}
	public void setTessera(Tessera tessera) {
		this.tessera = tessera;
	}
	
	public DipendenteCC getCliente() {
		return cliente;
	}
	public void setCliente(DipendenteCC cliente) {
		this.cliente = cliente;
	}
	public void setTipoOperazione(TipoOperazione tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	
	
	
	
	
	
	
}
