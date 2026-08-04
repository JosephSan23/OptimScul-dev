package backend.notification.application.port;

import backend.notification.application.NotificacionVista;
import backend.notification.domain.model.EstadoNotificacion;
import backend.notification.domain.model.NotificacionDestinatario;
import java.util.List;
import java.util.UUID;

public interface NotificacionDestinatarioRepository {
    NotificacionDestinatario save(NotificacionDestinatario d);
    List<NotificacionVista> bandeja(UUID usuarioId, int limite);
    long contarNoLeidas(UUID usuarioId);
    int marcarLeida(UUID id, UUID usuarioId);
    int marcarTodas(UUID usuarioId);
    int marcarLeidasDeConversacion(UUID usuarioId, UUID conversacionId);
    void actualizarEstado(UUID destinatarioId, EstadoNotificacion estado, String error);
}