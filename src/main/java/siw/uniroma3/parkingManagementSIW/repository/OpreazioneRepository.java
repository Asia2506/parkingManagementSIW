package siw.uniroma3.parkingManagementSIW.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;
import siw.uniroma3.parkingManagementSIW.model.Operazione;
import siw.uniroma3.parkingManagementSIW.model.Tessera;
import siw.uniroma3.parkingManagementSIW.model.TipoOperazione;

public interface OpreazioneRepository extends CrudRepository<Operazione,Long>{
	
	//@Query("SELECT o FROM Operazione o JOIN FETCH o.tessera t JOIN FETCH t.titolare c JOIN FETCH t.descrizioneTessera WHERE o.data = :data")
    //List<Operazione> findByData2(LocalDate data);
	
	@Query("SELECT o FROM Operazione o " +
	           "JOIN FETCH o.tessera t " +
	           "JOIN FETCH o.cliente cl " + // Carica il cliente dell'operazione
	           "JOIN FETCH t.descrizioneTessera dt " + // Carica la descrizione della tessera
	           "LEFT JOIN FETCH cl.datiFatturazione df " + // Carica i dati di fatturazione del cliente dell'operazione
	           "WHERE o.data = :data")//List<Operazione> findByData(LocalDate data);
	List<Operazione> findByData(LocalDate data);
	
	
	
	@Query("SELECT o FROM Operazione o " +
	           "JOIN FETCH o.cliente " + // Assicurati di caricare il cliente
	           "WHERE o.tessera.numero = :numeroTessera " +
	           "AND o.tipoOperazione = :smarrimento " +
	           "ORDER BY o.data DESC, o.id DESC")
	//List<Operazione> findLastSmarrimentoOperationByTessera(Long numeroTessera, TipoOperazione tipoOperazione);
	Optional<Operazione> findSmarrimentoOperationByTessera(Long numeroTessera, TipoOperazione smarrimento);
	
	
	@Query("SELECT o FROM Operazione o " +
	           "JOIN FETCH o.tessera t " +          // Alias 't' per la tessera dell'operazione
	           "JOIN FETCH o.cliente c " +           // Alias 'c' per il cliente dell'operazione
	           "JOIN FETCH t.titolare tit " +        // Alias 'tit' per il titolare della tessera
	           "JOIN FETCH t.descrizioneTessera dt " + // Per caricare anche la descrizione della tessera
	           "WHERE t.numero = :numeroTessera AND c = tit") // Condizioni combinate con AND
	List<Operazione> findOperazioniByTesseraAndTitolareCorrente(Long numeroTessera);
	
	
}
