package backend.security.infrastructure.persistence;

import backend.security.infrastructure.persistence.entity.RolPermisoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RolPermisoJpaRepository extends JpaRepository<RolPermisoEntity, UUID> {
}
