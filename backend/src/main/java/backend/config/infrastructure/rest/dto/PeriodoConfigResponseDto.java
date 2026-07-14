package backend.config.infrastructure.rest.dto;

import backend.academic.domain.model.PeriodoAcademico;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PeriodoConfigResponseDto {
    private UUID id;
    private UUID anioLectivoId;
    private Integer numero;
    private String nombre, descripcion;
    private String fechaInicio, fechaFin;
    private BigDecimal peso;
    private String estado;

    public static PeriodoConfigResponseDto desde(PeriodoAcademico p) {
        PeriodoConfigResponseDto d = new PeriodoConfigResponseDto();
        d.setId(p.getId());
        d.setAnioLectivoId(p.getAnioLectivoId());
        d.setNumero(p.getNumero() != null ? p.getNumero().intValue() : null);
        d.setNombre(p.getNombre());
        d.setDescripcion(p.getDescripcion());
        d.setFechaInicio(p.getFechaInicio() != null ? p.getFechaInicio().toString() : null);
        d.setFechaFin(p.getFechaFin() != null ? p.getFechaFin().toString() : null);
        d.setPeso(p.getPeso());
        d.setEstado(p.getEstado() != null ? p.getEstado().name() : null);
        return d;
    }
}