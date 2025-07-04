package edu.pe.vallegrande.Rosario_Chumpitaz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("ruc_consultas")
public class RucModel {
    
    @Id
    private Long id;
    
    private String ruc;
    
    private String razonSocial;
    
    private String condicion;
    
    private String direccion;
    
    private String departamento;
    
    private String provincia;
    
    private String distrito;
    
    private LocalDateTime fechaConsulta;
    
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaActualizacion;
}