package backend.finance.infrastructure.persistence;

import backend.finance.infrastructure.persistence.entity.BeneficioFinancieroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BeneficioFinancieroJpaRepository extends JpaRepository<BeneficioFinancieroEntity, UUID> {
}
