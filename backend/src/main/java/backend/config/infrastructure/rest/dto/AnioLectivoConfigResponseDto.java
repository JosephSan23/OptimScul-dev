package backend.config.infrastructure.rest.dto;

import backend.academic.domain.model.AnioLectivo;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AnioLectivoConfigResponseDto {
    private UUID id;
    private Integer anio;
    private String nombre, descripcion;
    private String fechaInicio, fechaFin;
    private String estado;
    private Boolean esActual;

    public static AnioLectivoConfigResponseDto desde(AnioLectivo a) {
        AnioLectivoConfigResponseDto d = new AnioLectivoConfigResponseDto();
        d.setId(a.getId());
        d.setAnio(a.getAnio() != null ? a.getAnio().intValue() : null);
        d.setNombre(a.getNombre());
        d.setDescripcion(a.getDescripcion());
        d.setFechaInicio(a.getFechaInicio() != null ? a.getFechaInicio().toString() : null);
        d.setFechaFin(a.getFechaFin() != null ? a.getFechaFin().toString() : null);
        d.setEstado(a.getEstado() != null ? a.getEstado().name() : null);
        d.setEsActual(a.getEsActual());
        return d;
    }
}