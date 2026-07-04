package backend.people.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import backend.people.domain.model.EstadoInstitucion;
import backend.people.domain.model.TipoInstitucion;

@Data
@NoArgsConstructor
public class InstitucionRequestDto {

    @NotNull
    private String codigo;

    @NotNull
    private String nombre;

    private String nombreCorto;

    @NotNull
    private TipoInstitucion tipoInstitucion;

    private String nit;

    private String dane;

    private String resolucionFuncionamiento;

    private String descripcion;

    private String correoContacto;

    private String telefonoContacto;

    private String sitioWeb;

    private String direccionPrincipal;

    private String ciudad;

    private String departamento;

    private String pais;

    private String logoUrl;

    private String zonaHoraria;

    private String moneda;

    private EstadoInstitucion estado;

}
