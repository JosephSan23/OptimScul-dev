package backend.enrollment.application.port;

import java.util.List;
import java.util.UUID;

public interface MatriculaConsultaRepository {
    List<MatriculaResumen> listarPorAnio(UUID institucionId, UUID anioLectivoId);
}