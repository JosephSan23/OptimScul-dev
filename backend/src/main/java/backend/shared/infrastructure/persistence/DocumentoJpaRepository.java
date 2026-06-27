package backend.shared.infrastructure.persistence;

import backend.shared.infrastructure.persistence.entity.DocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DocumentoJpaRepository extends JpaRepository<DocumentoEntity, UUID> {
}
