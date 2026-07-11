package backend.config.infrastructure.rest.dto;

import backend.people.domain.model.Sede;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class SedeResponseDto {
    private UUID id;
    private String codigo, nombre, descripcion, direccion, telefono, correo, ciudad, departamento, pais;
    private Boolean principal;
    private String estado;

    public static SedeResponseDto desde(Sede s) {
        SedeResponseDto d = new SedeResponseDto();
        d.setId(s.getId());
        d.setCodigo(s.getCodigo()); d.setNombre(s.getNombre()); d.setDescripcion(s.getDescripcion());
        d.setDireccion(s.getDireccion()); d.setTelefono(s.getTelefono()); d.setCorreo(s.getCorreo());
        d.setCiudad(s.getCiudad()); d.setDepartamento(s.getDepartamento()); d.setPais(s.getPais());
        d.setPrincipal(s.getPrincipal());
        d.setEstado(s.getEstado() != null ? s.getEstado().name() : null);
        return d;
    }
}