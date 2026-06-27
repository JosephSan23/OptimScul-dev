package backend.finance.infrastructure.persistence;

import backend.finance.infrastructure.persistence.entity.ConceptoCobroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ConceptoCobroJpaRepository extends JpaRepository<ConceptoCobroEntity, UUID> {
}
