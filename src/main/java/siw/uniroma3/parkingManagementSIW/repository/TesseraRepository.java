package siw.uniroma3.parkingManagementSIW.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.parkingManagementSIW.model.Operazione; // Importa Operazione
import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.model.TipoOperazione;

public interface TesseraRepository extends CrudRepository<Tessera,Long>{
	

	@Query("SELECT t FROM Tessera t " +
		       "JOIN FETCH t.titolare tit " +
		       "JOIN FETCH t.descrizioneTessera descr " +
		       "WHERE t.smarrita = false " +
		       "AND t.danneggiata = false " +
		       "AND tit IS NOT NULL")
	List<Tessera> findTessereAttive();
	
	
	
	/*@Query("SELECT o FROM Operazione o " +
	           "JOIN FETCH o.cliente " + // Assicurati di caricare il cliente
	           "WHERE o.tessera.numero = :numeroTessera " +
	           "AND o.tipoOperazione = :tipoOperazione " +
	           "ORDER BY o.data DESC, o.id DESC")
	List<Operazione> findLastOperationByTesseraAndTipo(Long numeroTessera, TipoOperazione tipoOperazione);
	
	
	@Query("SELECT o FROM Operazione o "
			+ "WHERE o.cliente=o.tessera.titolare")
	List<Operazione> findOperazioniTesseraByTitolareAttuale();*/
	
	
}
