package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.EscalaValorativaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EscalaValorativaJpaRepository extends JpaRepository<EscalaValorativaEntity, UUID> {
}
