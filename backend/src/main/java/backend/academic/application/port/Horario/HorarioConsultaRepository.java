package backend.academic.application.port.Horario;

import java.util.List;
import java.util.UUID;

public interface HorarioConsultaRepository {
    List<HorarioResumen> listarPorGrupo(UUID institucionId, UUID grupoId);
    boolean existeChoqueProfesor(UUID profesorId, UUID anioLectivoId, String dia,
                                 String horaInicio, String horaFin, UUID excluirHorarioId);
    boolean existeChoqueGrupo(UUID grupoId, UUID anioLectivoId, String dia,
                              String horaInicio, String horaFin, UUID excluirHorarioId);
}