package backend.enrollment.infrastructure.rest.dto;

import java.util.UUID;

import backend.enrollment.domain.model.Matricula;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatriculaResponseDto {

    private UUID id;
    private UUID estudianteId, anioLectivoId, grupoId;
    private String codigoMatricula, tipo, estado, fechaMatricula, observaciones;
    
    public static MatriculaResponseDto desde(Matricula m) {
        MatriculaResponseDto d = new MatriculaResponseDto();
        d.setId(m.getId());
        d.setEstudianteId(m.getEstudianteId());
        d.setAnioLectivoId(m.getAnioLectivoId());
        d.setGrupoId(m.getGrupoId());
        d.setCodigoMatricula(m.getCodigoMatricula());
        d.setTipo(m.getTipo() != null ? m.getTipo().toString() : null);
        d.setEstado(m.getEstado() != null ? m.getEstado().toString() : null);
        d.setObservaciones(m.getObservaciones());
        d.setFechaMatricula(m.getFechaMatricula() != null ? m.getFechaMatricula().toString() : null);
        

        return d;
    }
}
