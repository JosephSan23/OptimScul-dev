package backend.community.application.port;

import java.util.Optional;
import java.util.UUID;

public interface CredencialesEstudianteRepository {

    /** Trae los datos del estudiante y de su acudiente destino (con correo o principal). */
    Optional<DatosCredencialEstudiante> obtener(UUID estudianteId);
}
