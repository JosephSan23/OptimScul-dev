package backend.finance.infrastructure.persistence;

import backend.finance.infrastructure.persistence.entity.EstudianteBeneficioFinancieroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EstudianteBeneficioFinancieroJpaRepository extends JpaRepository<EstudianteBeneficioFinancieroEntity, UUID> {
}
