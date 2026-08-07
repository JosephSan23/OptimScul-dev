package backend.community.application.port;

/**
 * Datos necesarios para enviar las credenciales de acceso de un estudiante.
 * Incluye los del propio estudiante y los del acudiente elegido como destino
 * (el acudiente con correo; en su defecto, el principal). Los campos del
 * acudiente son null cuando el estudiante aún no tiene acudientes vinculados.
 */
public record DatosCredencialEstudiante(
        String estudianteNombre,
        String estudianteUsername,
        String estudianteCorreo,
        String acudienteNombre,
        String acudienteUsername,
        String acudienteCorreo) {
}
