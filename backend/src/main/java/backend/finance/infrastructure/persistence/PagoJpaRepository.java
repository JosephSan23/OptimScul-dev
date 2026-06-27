package backend.finance.infrastructure.persistence;

import backend.finance.infrastructure.persistence.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PagoJpaRepository extends JpaRepository<PagoEntity, UUID> {
}
