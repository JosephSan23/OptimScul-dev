package backend.admission.infrastructure.persistence;

import backend.admission.infrastructure.persistence.entity.PostulacionPersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PostulacionPersonaJpaRepository extends JpaRepository<PostulacionPersonaEntity, UUID> {
}
