package backend.academic.application.port;

import java.util.UUID;

public interface MarcaAsistenciaFila {
    UUID getEstudianteId();
    UUID getSesionId();
    String getTipo();
}