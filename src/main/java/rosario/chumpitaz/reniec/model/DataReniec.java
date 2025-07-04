package rosario.chumpitaz.reniec.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("ApiReniec")
public class DataReniec {

    @Id
    @Column("id")
    private Long Id;

    @Column("ruc")
    private String ruc;

    @Column("razon_social")
    private String razonSocial;

    @Column("condicion")
    private String condicion;

    @Column("direccion")
    private String direccion;

    @Column("departamento")
    private String departamento;

    @Column("provincia")
    private String provincia;

    @Column("distrito")
    private String distrito;

    @Column("estado")
    private String estado;
}
