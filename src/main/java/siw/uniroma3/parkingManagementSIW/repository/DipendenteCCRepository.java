package siw.uniroma3.parkingManagementSIW.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;


public interface DipendenteCCRepository extends CrudRepository<DipendenteCC,Long>{
	
	//@Query("SELECT d FROM DipendenteCC d WHERE d.nome = :nome AND d.cognome = :cognome")
    //List<DipendenteCC> findByNomeAndCognome(@Param("nome") String nome, @Param("cognome") String cognome);
	// In DipendenteCCRepository.java
	@Query("SELECT d FROM DipendenteCC d LEFT JOIN FETCH d.tessera WHERE d.nome = :nome AND d.cognome = :cognome")
	List<DipendenteCC> findByNomeAndCognome(@Param("nome") String nome, @Param("cognome") String cognome);
}
