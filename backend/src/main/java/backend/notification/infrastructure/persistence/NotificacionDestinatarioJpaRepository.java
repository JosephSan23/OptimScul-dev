package backend.notification.infrastructure.persistence;

import backend.notification.infrastructure.persistence.entity.NotificacionDestinatarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface NotificacionDestinatarioJpaRepository extends JpaRepository<NotificacionDestinatarioEntity, UUID> {
}
