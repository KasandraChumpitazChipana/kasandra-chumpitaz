package edu.pe.vallegrande.Rosario_Chumpitaz;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para consulta de RUC")
public class RucDto {
    
    @Schema(description = "Número de RUC", example = "20131312955")
    @NotBlank(message = "El RUC no puede estar vacío")
    @Size(min = 11, max = 11, message = "El RUC debe tener 11 dígitos")
    @Pattern(regexp = "^[0-9]{11}$", message = "El RUC debe contener solo números")
    private String ruc;
    
    @Schema(description = "Razón social de la empresa", example = "SUPERINTENDENCIA NACIONAL DE ADUANAS Y DE ADMINISTRACION TRIBUTARIA - SUNAT")
    private String razonSocial;
    
    @Schema(description = "Condición del contribuyente", example = "HABIDO")
    private String condicion;
    
    @Schema(description = "Dirección fiscal", example = "AV. GARCILASO DE LA VEGA NRO. 1472 LIMA LIMA LIMA")
    private String direccion;
    
    @Schema(description = "Departamento", example = "LIMA")
    private String departamento;
    
    @Schema(description = "Provincia", example = "LIMA")
    private String provincia;
    
    @Schema(description = "Distrito", example = "LIMA")
    private String distrito;
}