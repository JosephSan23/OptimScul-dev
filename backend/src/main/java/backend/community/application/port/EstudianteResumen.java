package backend.community.application.port;

import java.time.LocalDate;
import java.util.UUID;

public interface EstudianteResumen {
    UUID getEstudianteId();
    String getCodigoEstudiante();
    String getEstado();
    LocalDate getFechaIngreso();
    UUID getUsuarioId();
    String getUsername();
    String getPrimerNombre();
    String getPrimerApellido();
    String getNumeroDocumento();
    String getCorreo();

    /** true si hay un correo alcanzable (del estudiante o de alguno de sus acudientes). */
    Boolean getCorreoAlcanzable();
}
