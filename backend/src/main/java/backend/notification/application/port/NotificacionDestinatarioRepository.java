package backend.notification.application.port;

import backend.notification.domain.model.NotificacionDestinatario;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificacionDestinatarioRepository {

    NotificacionDestinatario save(NotificacionDestinatario entity);
    Optional<NotificacionDestinatario> findById(UUID id);
    List<NotificacionDestinatario> findAll();
    void deleteById(UUID id);
}
