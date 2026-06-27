package backend.security.infrastructure.persistence;

import backend.security.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, UUID> {
}
