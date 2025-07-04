package edu.pe.vallegrande.Rosario_Chumpitaz.service;

import edu.pe.vallegrande.Rosario_Chumpitaz.dto.ExternalRucDto;
import edu.pe.vallegrande.Rosario_Chumpitaz.dto.RucDto;
import edu.pe.vallegrande.Rosario_Chumpitaz.model.RucModel;
import edu.pe.vallegrande.Rosario_Chumpitaz.repository.RucRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RucService {

    private final RucRepository rucRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${external.api.ruc.url}")
    private String externalApiUrl;

    @Value("${external.api.ruc.token}")
    private String apiToken;

    public Mono<RucDto> consultarRuc(String ruc) {
        log.info("Consultando RUC: {}", ruc);

        return rucRepository.findByRuc(ruc)
                .doOnNext(model -> log.info("RUC encontrado en BD: {}", model.getRuc()))
                .map(this::mapToDto)
                .switchIfEmpty(
                        consultarRucExterno(ruc)
                                .flatMap(this::guardarRuc)
                                .map(this::mapToDto)
                                .doOnNext(dto -> log.info("RUC consultado y guardado: {}", dto.getRuc()))
                );
    }

    private Mono<ExternalRucDto> consultarRucExterno(String ruc) {
        log.info("Consultando API externa para RUC: {}", ruc);

        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(externalApiUrl)
                        .queryParam("numero", ruc)
                        .build())
                .header("Authorization", "Bearer " + apiToken)
                .retrieve()
                .bodyToMono(ExternalRucDto.class)
                .doOnNext(response -> log.info("Respuesta API externa recibida para RUC: {}", response.getRuc()))
                .doOnError(error -> log.error("Error al consultar API externa: {}", error.getMessage()))
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al consultar RUC externo", error)));
    }

    private Mono<RucModel> guardarRuc(ExternalRucDto externalDto) {
        if (externalDto == null || externalDto.getRuc() == null) {
            log.warn("Datos externos nulos o incompletos, no se puede guardar");
            return Mono.error(new IllegalArgumentException("Datos externos nulos o incompletos"));
        }

        RucModel rucModel = RucModel.builder()
                .ruc(externalDto.getRuc())
                .razonSocial(externalDto.getRazonSocial())
                .condicion(externalDto.getCondicion())
                .direccion(externalDto.getDireccion())
                .departamento(externalDto.getDepartamento())
                .provincia(externalDto.getProvincia())
                .distrito(externalDto.getDistrito())
                .fechaConsulta(LocalDateTime.now())
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        log.info("Guardando en BD RUC: {}", rucModel.getRuc());
        return rucRepository.save(rucModel);
    }

    private RucDto mapToDto(RucModel model) {
        return RucDto.builder()
                .ruc(model.getRuc())
                .razonSocial(model.getRazonSocial())
                .condicion(model.getCondicion())
                .direccion(model.getDireccion())
                .departamento(model.getDepartamento())
                .provincia(model.getProvincia())
                .distrito(model.getDistrito())
                .build();
    }
}
