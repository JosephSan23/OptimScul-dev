package backend.finance.infrastructure.persistence;

import backend.finance.infrastructure.persistence.entity.CuentaCobroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CuentaCobroJpaRepository extends JpaRepository<CuentaCobroEntity, UUID> {
}
