package backend.enrollment.application.port;

import java.util.UUID;

public interface MatriculaResumen {
    UUID getId();
    UUID getEstudianteId();
    UUID getGrupoId();
    String getCodigoMatricula();
    String getEstudianteNombre();
    String getCodigoEstudiante();
    String getNumeroDocumento();
    String getGrupoNombre();
    String getGradoNombre();
    String getTipo();
    String getEstado();
    String getFechaMatricula();
}