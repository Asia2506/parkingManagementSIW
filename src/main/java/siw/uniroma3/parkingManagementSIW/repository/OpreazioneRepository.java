package siw.uniroma3.parkingManagementSIW.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import siw.uniroma3.parkingManagementSIW.model.DipendenteCC;
import siw.uniroma3.parkingManagementSIW.model.Operazione;

public interface OpreazioneRepository extends CrudRepository<Operazione,Long>{
	
	@Query("SELECT o FROM Operazione o JOIN FETCH o.tessera t JOIN FETCH t.titolare c JOIN FETCH t.descrizioneTessera WHERE o.data = :data AND TYPE(c) = :dipendenteCCType")
    List<Operazione> findByData(LocalDate data, @Param("dipendenteCCType") Class<DipendenteCC> dipendenteCCType);

}
