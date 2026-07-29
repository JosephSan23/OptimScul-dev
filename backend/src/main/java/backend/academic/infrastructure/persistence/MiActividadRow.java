package backend.academic.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface MiActividadRow {
    UUID getActividadId();
    String getTitulo();
    String getTipo();
    LocalDateTime getFechaEntrega();
    LocalDateTime getFechaCierre();
    BigDecimal getNotaMaxima();
    String getAsignatura();
    UUID getCargaId();
    String getEstadoEntrega();
    BigDecimal getNotaObtenida();
}