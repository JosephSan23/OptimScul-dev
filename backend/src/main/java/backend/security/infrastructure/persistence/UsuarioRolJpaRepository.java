package backend.security.infrastructure.persistence;

import backend.security.infrastructure.persistence.entity.UsuarioRolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioRolJpaRepository extends JpaRepository<UsuarioRolEntity, UUID> {
}
