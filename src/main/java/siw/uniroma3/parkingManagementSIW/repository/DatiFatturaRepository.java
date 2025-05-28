package siw.uniroma3.parkingManagementSIW.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import siw.uniroma3.parkingManagementSIW.model.DatiFattura;

public interface DatiFatturaRepository extends CrudRepository<DatiFattura,Long>{
	
	// Query per trovare DatiFattura tramite ragioneSociale (potrebbero essercene più di uno)
    @Query("SELECT df FROM DatiFattura df WHERE df.ragioneSociale = :ragioneSociale")
    public List<DatiFattura> findByRagioneSociale(@Param("ragioneSociale") String ragioneSociale);


}
