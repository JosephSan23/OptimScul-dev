package backend.academic.application.port;

import java.util.UUID;

public interface EstudianteDeClase {
    UUID getEstudianteId();
    UUID getMatriculaId();
    String getCodigoEstudiante();
    String getNombre();
    String getNumeroDocumento();
}