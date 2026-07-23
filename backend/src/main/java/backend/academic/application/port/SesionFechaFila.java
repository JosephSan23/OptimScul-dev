package backend.academic.application.port;

import java.util.UUID;

public interface SesionFechaFila {
    UUID getSesionId();
    String getFecha();   
}