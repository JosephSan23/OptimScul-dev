package backend.notification.infrastructure.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificacionDto(
        UUID id, UUID notificacionId, String tipo, String titulo, String mensaje,
        String moduloRelacionado, UUID entidadRelacionadaId, Integer prioridad,
        String estado, LocalDateTime leidaEn, LocalDateTime createdAt) {}