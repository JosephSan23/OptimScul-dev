package backend.academic.application.port.Acudiente;


import java.util.UUID;

public interface HijoResumen {
    UUID getEstudianteId();
    String getNombre();
    String getCodigoEstudiante();
    String getNumeroDocumento();
}