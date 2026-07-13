package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.AnioLectivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AnioLectivoJpaRepository extends JpaRepository<AnioLectivoEntity, UUID> {
    java.util.List<AnioLectivoEntity> findByInstitucionId(UUID institucionId);
}
