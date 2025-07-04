package rosario.chumpitaz.reniec.service;

import lombok.extern.slf4j.Slf4j;
import rosario.chumpitaz.reniec.dto.apiReniec;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import rosario.chumpitaz.reniec.model.DataReniec;
import rosario.chumpitaz.reniec.repository.ReniecRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReniecService {

    private static final String RENIEC_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1hcmlhLmF2aWxhQHZhbGxlZ3JhbmRlLmVkdS5wZSJ9.i8HTp6A8ENt2wxuu2NyEvyyw_VZSsvmiaXr7eG0ltgA";
    private final WebClient webClient;
    private final ReniecRepository reniecRepository;

    public ReniecService(WebClient.Builder webClientBuilder, ReniecRepository reniecRepository) {
        this.webClient = webClientBuilder.baseUrl("https://dniruc.apisperu.com/api/v1").build();
        this.reniecRepository = reniecRepository;
    }

    public Flux<DataReniec> getAll() {
        return reniecRepository.findAll();
    }

    public Mono<DataReniec> fetchAndSaveRucData(String ruc) {
        String url = "/ruc/" + ruc + "?token=" + RENIEC_TOKEN;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(apiReniec.class)
                .doOnNext(data -> log.info("Datos obtenidos de SUNAT: {}", data))
                .map(dto -> {
                    // Mapear manualmente al modelo que guarda en la BD
                    DataReniec entity = new DataReniec();
                    entity.setRuc(dto.getRuc());
                    entity.setRazonSocial(dto.getRazonSocial());
                    entity.setCondicion(dto.getCondicion());
                    entity.setDireccion(dto.getDireccion());
                    entity.setDepartamento(dto.getDepartamento());
                    entity.setProvincia(dto.getProvincia());
                    entity.setDistrito(dto.getDistrito());
                    entity.setEstado("A"); // Establecer estado activo por defecto
                    return entity;
                })
                .flatMap(reniecRepository::save)
                .doOnError(error -> {
                    if (error instanceof WebClientResponseException ex) {
                        log.error("Error al consumir API SUNAT: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString());
                    } else {
                        log.error("Error desconocido: ", error);
                    }
                });
    }

    public Mono<DataReniec> desactivar(Long id) {
        return reniecRepository.findById(id)
                .flatMap(data -> {
                    data.setEstado("I");
                    return reniecRepository.save(data);
                });
    }

    public Mono<DataReniec> restaurar(Long id) {
        return reniecRepository.findById(id)
                .flatMap(data -> {
                    data.setEstado("A");
                    return reniecRepository.save(data);
                });
    }
}