package backend.people.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Institucion {

    private UUID id;
    private String codigo;
    private String nombre;
    private String nombreCorto;
    private TipoInstitucion tipoInstitucion;
    private String nit;
    private String dane;
    private String resolucionFuncionamiento;
    private String descripcion;
    private String correoContacto;
    private String telefonoContacto;
    private String sitioWeb;
    private String dominioCorreo;
    private String direccionPrincipal;
    private String ciudad;
    private String departamento;
    private String pais;
    private String logoUrl;
    private String zonaHoraria;
    private String moneda;
    private EstadoInstitucion estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
