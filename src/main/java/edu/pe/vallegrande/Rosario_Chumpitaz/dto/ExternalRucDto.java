package edu.pe.vallegrande.Rosario_Chumpitaz.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalRucDto {
    
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String tipo;
    private String estado;
    private String condicion;
    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;
    private String fechaInscripcion;
    private String sistEmsion;
    private String sistContabilidad;
    private String actExterior;
    private String fechaEmisorFe;
    private String fechaPle;
    private String fechaBaja;
    private String profesion;
    private String ubigeo;
    private String capital;
}