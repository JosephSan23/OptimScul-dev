package backend.finance.infrastructure.persistence;

import backend.finance.infrastructure.persistence.entity.CuentaCobroBeneficioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CuentaCobroBeneficioJpaRepository extends JpaRepository<CuentaCobroBeneficioEntity, UUID> {
}
