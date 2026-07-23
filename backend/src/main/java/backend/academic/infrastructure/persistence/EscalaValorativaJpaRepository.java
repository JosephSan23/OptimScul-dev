package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.EscalaValorativaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface EscalaValorativaJpaRepository extends JpaRepository<EscalaValorativaEntity, UUID> {
    List<EscalaValorativaEntity>
        findByInstitucionIdOrderByOrdenAsc(UUID institucionId);
}
