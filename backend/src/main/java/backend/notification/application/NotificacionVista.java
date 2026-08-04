package backend.notification.application;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificacionVista(
        UUID id,                 // id del destinatario (para marcar leída)
        UUID notificacionId,
        String tipo,
        String titulo,
        String mensaje,
        String moduloRelacionado,
        UUID entidadRelacionadaId,
        Integer prioridad,
        String estado,
        LocalDateTime leidaEn,
        LocalDateTime createdAt) {}