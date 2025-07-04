package rosario.chumpitaz.reniec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class apiReniec {

    private Long Id;
    private String ruc;
    private String razonSocial;
    private String condicion;
    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;
}
