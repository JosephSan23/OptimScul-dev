package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.GradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;


public interface GradoJpaRepository extends JpaRepository<GradoEntity, UUID> {
    List<GradoEntity> findByInstitucionIdOrderByOrdenAsc(UUID institucionId);
    boolean existsByInstitucionIdAndCodigoIgnoreCase(UUID institucionId, String codigo);
}
