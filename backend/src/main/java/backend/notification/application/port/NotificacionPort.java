package backend.notification.application.port;

import backend.notification.domain.model.CanalNotificacion;
import backend.notification.domain.model.TipoNotificacion;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NotificacionPort {

    void notificar(NuevaNotificacion nueva);

    record NuevaNotificacion(
            UUID institucionId,
            TipoNotificacion tipo,
            String titulo,
            String mensaje,
            String moduloRelacionado,
            UUID entidadRelacionadaId,
            short prioridad,
            List<UUID> destinatarios,
            Set<CanalNotificacion> canales,
            UUID createdBy) {}
}