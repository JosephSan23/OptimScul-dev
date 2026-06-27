package backend.security.infrastructure.persistence;

import backend.security.infrastructure.persistence.entity.UsuarioInstitucionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioInstitucionJpaRepository extends JpaRepository<UsuarioInstitucionEntity, UUID> {
}
