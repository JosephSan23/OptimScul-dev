package backend.community.application.port;

/**
 * Datos necesarios para enviar las credenciales de acceso de un estudiante.
 * Incluye los del propio estudiante y los del acudiente principal (destino).
 * Los campos del acudiente son null cuando el estudiante no tiene principal.
 * Los flags "pendiente" indican que esa cuenta aún no ha iniciado sesión.
 */
public record DatosCredencialEstudiante(
        String estudianteNombre,
        String estudianteUsername,
        String estudianteCorreo,
        String acudienteNombre,
        String acudienteUsername,
        String acudienteCorreo,
        boolean estudiantePendiente,
        boolean acudientePendiente) {
}
