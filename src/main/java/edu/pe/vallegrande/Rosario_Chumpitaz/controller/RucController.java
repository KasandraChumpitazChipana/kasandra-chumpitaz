package edu.pe.vallegrande.Rosario_Chumpitaz.controller;

import edu.pe.vallegrande.Rosario_Chumpitaz.dto.RucDto;
import edu.pe.vallegrande.Rosario_Chumpitaz.RucService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ruc")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "RUC", description = "API para consulta de RUC")
public class RucController {

    private final RucService rucService;

    @GetMapping(value = "/consultar/{ruc}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Consultar RUC", description = "Consulta información de un RUC específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RUC encontrado",
                    content = @Content(schema = @Schema(implementation = RucDto.class))),
            @ApiResponse(responseCode = "400", description = "RUC inválido"),
            @ApiResponse(responseCode = "404", description = "RUC no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Mono<ResponseEntity<?>> consultarRuc(
            @Parameter(description = "Número de RUC de 11 dígitos", example = "20131312955")
            @PathVariable
            @NotBlank(message = "El RUC no puede estar vacío")
            @Size(min = 11, max = 11, message = "El RUC debe tener 11 dígitos")
            @Pattern(regexp = "^[0-9]{11}$", message = "El RUC debe contener solo números")
            String ruc) {

        log.info("Solicitud de consulta RUC: {}", ruc);

        return rucService.consultarRuc(ruc)
                .map(rucDto -> {
                    log.info("RUC consultado exitosamente: {}", rucDto.getRuc());
                    return ResponseEntity.ok(rucDto);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .onErrorResume(error -> {
                    log.error("Error al consultar RUC {}: {}", ruc, error.getMessage());
                    return Mono.just(ResponseEntity.status(500).body(
                            String.format("{\"error\": \"%s\"}", error.getMessage())
                    ));
                });
    }

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Health Check", description = "Verificar el estado del servicio")
    @ApiResponse(responseCode = "200", description = "Servicio disponible")
    public Mono<ResponseEntity<String>> healthCheck() {
        return Mono.just(ResponseEntity.ok("{\"status\":\"UP\",\"service\":\"RUC-Service\"}"));
    }
}
