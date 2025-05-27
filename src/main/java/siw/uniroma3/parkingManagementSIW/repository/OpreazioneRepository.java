package siw.uniroma3.parkingManagementSIW.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import siw.uniroma3.parkingManagementSIW.model.Operazione;

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
}
