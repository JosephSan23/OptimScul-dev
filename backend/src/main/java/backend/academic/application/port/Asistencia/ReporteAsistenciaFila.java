package backend.academic.application.port.Asistencia;

import java.util.UUID;

public interface ReporteAsistenciaFila {
    UUID getEstudianteId();
    String getNombre();
    String getCodigoEstudiante();
    Long getPresente();
    Long getAusente();
    Long getTarde();
    Long getJustificada();
    Long getTotalRegistros();
}