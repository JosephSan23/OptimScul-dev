package backend.notification.application.port;

import backend.notification.domain.model.Notificacion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificacionRepository {

    Notificacion save(Notificacion entity);
    Optional<Notificacion> findById(UUID id);
    List<Notificacion> findAll();
    void deleteById(UUID id);
}
