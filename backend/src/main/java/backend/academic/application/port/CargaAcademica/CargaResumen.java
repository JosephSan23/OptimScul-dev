package backend.academic.application.port.CargaAcademica;

import java.util.UUID;

public interface CargaResumen {
    UUID getId();
    UUID getProfesorId();
    UUID getGrupoId();
    UUID getAsignaturaId();
    String getProfesorNombre();
    String getCodigoProfesor();
    String getAsignaturaNombre();
    String getGrupoNombre();
    String getGrupoCodigo();
    String getGradoNombre();
    Short getIntensidadHorariaSemanal();
    String getEstado();
    String getObservaciones();
}
