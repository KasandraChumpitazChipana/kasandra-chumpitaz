package rosario.chumpitaz.reniec.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import rosario.chumpitaz.reniec.model.DataReniec;
import reactor.core.publisher.Flux;

@Repository
public interface ReniecRepository extends ReactiveCrudRepository<DataReniec, Long> {

    Flux<DataReniec> findByEstado(String estado);
}
