package backend.academic.application.port;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MisActividadesConsultaRepository {

    List<MiActividadFila> listar(UUID estudianteId, UUID anioId);

    record MiActividadFila(
            UUID actividadId,
            String titulo,
            String tipo,
            LocalDateTime fechaEntrega,
            LocalDateTime fechaCierre,
            BigDecimal notaMaxima,
            String asignatura,
            UUID cargaId,
            String estadoEntrega,
            BigDecimal notaObtenida
    ) {}
}