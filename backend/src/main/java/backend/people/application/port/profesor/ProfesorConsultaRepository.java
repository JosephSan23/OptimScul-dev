package backend.people.application.port.profesor;

import java.util.List;
import java.util.UUID;

public interface ProfesorConsultaRepository {
    List<ProfesorResumen> listarPorInstitucion(UUID institucionId);
}