package backend.academic.application.port.Horario;

import java.util.UUID;

public interface HorarioResumen {
    UUID getId();
    UUID getCargaAcademicaId();
    String getDiaSemana();
    String getHoraInicio();
    String getHoraFin();
    String getAula();
    Boolean getActivo();
    String getAsignaturaNombre();
    String getProfesorNombre();
    String getSedeNombre();
}