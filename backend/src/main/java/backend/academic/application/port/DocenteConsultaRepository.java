package backend.academic.application.port;

import backend.academic.application.port.Asistencia.ReporteAsistenciaFila;
import backend.academic.application.port.Horario.HorarioResumen;
import java.util.List;
import java.util.UUID;

public interface DocenteConsultaRepository {
    List<MiClaseResumen> misClases(UUID profesorId, UUID anioLectivoId);
    List<HorarioResumen> miHorario(UUID profesorId, UUID anioLectivoId);
    List<EstudianteDeClase> estudiantesDeGrupo(UUID grupoId);
    List<ReporteAsistenciaFila> reporteAsistencia(UUID cargaId);
}