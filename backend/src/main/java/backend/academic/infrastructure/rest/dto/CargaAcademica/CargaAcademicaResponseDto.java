package backend.academic.infrastructure.rest.dto.CargaAcademica;

import backend.academic.domain.model.CargaAcademica;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CargaAcademicaResponseDto {
    private UUID id;
    private UUID anioLectivoId, grupoId, asignaturaId, profesorId;
    private Short intensidadHorariaSemanal;
    private String fechaInicio, fechaFin, estado, observaciones;

    public static CargaAcademicaResponseDto desde(CargaAcademica c) {
        CargaAcademicaResponseDto d = new CargaAcademicaResponseDto();
        d.setId(c.getId());
        d.setAnioLectivoId(c.getAnioLectivoId());
        d.setGrupoId(c.getGrupoId());
        d.setAsignaturaId(c.getAsignaturaId());
        d.setProfesorId(c.getProfesorId());
        d.setIntensidadHorariaSemanal(c.getIntensidadHorariaSemanal());
        d.setFechaInicio(c.getFechaInicio() != null ? c.getFechaInicio().toString() : null);
        d.setFechaFin(c.getFechaFin() != null ? c.getFechaFin().toString() : null);
        d.setEstado(c.getEstado() != null ? c.getEstado().name() : null);
        d.setObservaciones(c.getObservaciones());
        return d;
    }
}