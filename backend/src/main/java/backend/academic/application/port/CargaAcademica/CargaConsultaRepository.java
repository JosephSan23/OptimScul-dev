package backend.academic.application.port.CargaAcademica;

import java.util.List;
import java.util.UUID;

public interface CargaConsultaRepository {
    List<CargaResumen> listarPorAnio(UUID institucionId, UUID anioLectivoId);
}