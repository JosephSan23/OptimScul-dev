package backend.security.infrastructure.persistence;

import backend.security.infrastructure.persistence.entity.PermisoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PermisoJpaRepository extends JpaRepository<PermisoEntity, UUID> {
}
