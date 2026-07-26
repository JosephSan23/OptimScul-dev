package backend.academic.application.port.Asistencia;

import backend.academic.application.port.Horario.HorarioResumen;
import java.util.List;
import java.util.UUID;

public interface PortalFamiliaConsultaRepository {
    List<HorarioResumen> horarioDeGrupo(UUID grupoId, UUID anioLectivoId);
    List<AsistenciaMateriaFila> asistenciaPorMateria(UUID estudianteId, UUID anioLectivoId);
}