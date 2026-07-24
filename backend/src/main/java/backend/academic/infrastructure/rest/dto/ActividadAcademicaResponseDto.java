package backend.academic.infrastructure.rest.dto;

import backend.academic.domain.model.ActividadAcademica;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ActividadAcademicaResponseDto {
    private UUID id;
    private UUID periodoAcademicoId;
    private String tipo;
    private String titulo;
    private String descripcion;
    private String fechaEntrega;
    private String fechaCierre;
    private BigDecimal porcentaje;
    private BigDecimal notaMaxima;
    private Boolean permiteEntregaTardia;
    private String estado;

    public static ActividadAcademicaResponseDto desde(ActividadAcademica a) {
        ActividadAcademicaResponseDto d = new ActividadAcademicaResponseDto();
        d.setId(a.getId());
        d.setPeriodoAcademicoId(a.getPeriodoAcademicoId());
        d.setTipo(a.getTipo() != null ? a.getTipo().name() : null);
        d.setTitulo(a.getTitulo());
        d.setDescripcion(a.getDescripcion());
        d.setFechaEntrega(a.getFechaEntrega() != null ? a.getFechaEntrega().toLocalDate().toString() : null);
        d.setFechaCierre(a.getFechaCierre() != null ? a.getFechaCierre().toLocalDate().toString() : null);
        d.setPorcentaje(a.getPorcentaje());
        d.setNotaMaxima(a.getNotaMaxima());
        d.setPermiteEntregaTardia(a.getPermiteEntregaTardia());
        d.setEstado(a.getEstado() != null ? a.getEstado().name() : null);
        return d;
    }
}