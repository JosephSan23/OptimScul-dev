package backend.security.infrastructure.persistence;

import backend.security.infrastructure.persistence.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RolJpaRepository extends JpaRepository<RolEntity, UUID> {
}
