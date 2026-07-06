package backend.people.infrastructure.rest.dto;

import backend.people.domain.model.EstadoInstitucion;
import backend.people.domain.model.TipoInstitucion;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class InstitucionResponseDto {

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
}