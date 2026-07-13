package backend.config.infrastructure.rest.dto;

import backend.people.domain.model.Jornada;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class JornadaResponseDto {
    private UUID id;
    private String codigo, nombre, descripcion;
    private String horaInicio, horaFin;
    private String estado;

    public static JornadaResponseDto desde(Jornada j) {
        JornadaResponseDto d = new JornadaResponseDto();
        d.setId(j.getId());
        d.setCodigo(j.getCodigo()); d.setNombre(j.getNombre()); d.setDescripcion(j.getDescripcion());
        d.setHoraInicio(j.getHoraInicio() != null ? j.getHoraInicio().toString() : null);
        d.setHoraFin(j.getHoraFin() != null ? j.getHoraFin().toString() : null);
        d.setEstado(j.getEstado() != null ? j.getEstado().name() : null);
        return d;
    }
}