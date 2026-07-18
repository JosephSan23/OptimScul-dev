package backend.people.application.port.profesor;

import java.util.UUID;

public interface ProfesorResumen {
    UUID getId();
    String getNombre();
    String getCodigoProfesor();
    String getEspecialidad();
    String getEstado();
}