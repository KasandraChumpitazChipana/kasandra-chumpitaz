package edu.pe.vallegrande.Rosario_Chumpitaz.repository;
import edu.pe.vallegrande.Rosario_Chumpitaz.model.RucModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RucRepository extends R2dbcRepository<RucModel, Long> {
    
    Mono<RucModel> findByRuc(String ruc);
    
    Mono<Boolean> existsByRuc(String ruc);
}