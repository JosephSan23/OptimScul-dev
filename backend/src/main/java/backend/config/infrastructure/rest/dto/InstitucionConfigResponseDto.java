package backend.config.infrastructure.rest.dto;

import backend.people.domain.model.Institucion;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstitucionConfigResponseDto {
    // Solo lectura (super admin)
    private String codigo, tipoInstitucion, estado, dominioCorreo;
    // Editables
    private String nombre, nombreCorto, descripcion, nit, dane, resolucionFuncionamiento,
            correoContacto, telefonoContacto, sitioWeb, direccionPrincipal,
            ciudad, departamento, pais, zonaHoraria, moneda;

    public static InstitucionConfigResponseDto desde(Institucion i) {
        InstitucionConfigResponseDto d = new InstitucionConfigResponseDto();
        d.setCodigo(i.getCodigo());
        d.setTipoInstitucion(i.getTipoInstitucion() != null ? i.getTipoInstitucion().name() : null);
        d.setEstado(i.getEstado() != null ? i.getEstado().name() : null);
        d.setDominioCorreo(i.getDominioCorreo());
        d.setNombre(i.getNombre()); d.setNombreCorto(i.getNombreCorto()); d.setDescripcion(i.getDescripcion());
        d.setNit(i.getNit()); d.setDane(i.getDane()); d.setResolucionFuncionamiento(i.getResolucionFuncionamiento());
        d.setCorreoContacto(i.getCorreoContacto()); d.setTelefonoContacto(i.getTelefonoContacto());
        d.setSitioWeb(i.getSitioWeb()); d.setDireccionPrincipal(i.getDireccionPrincipal());
        d.setCiudad(i.getCiudad()); d.setDepartamento(i.getDepartamento()); d.setPais(i.getPais());
        d.setZonaHoraria(i.getZonaHoraria()); d.setMoneda(i.getMoneda());
        return d;
    }
}