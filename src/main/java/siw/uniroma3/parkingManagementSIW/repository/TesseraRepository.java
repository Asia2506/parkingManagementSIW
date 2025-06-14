package siw.uniroma3.parkingManagementSIW.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.parkingManagementSIW.model.Operazione; // Importa Operazione
import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.model.TipoOperazione;

public interface TesseraRepository extends CrudRepository<Tessera,Long>{
	
	
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
