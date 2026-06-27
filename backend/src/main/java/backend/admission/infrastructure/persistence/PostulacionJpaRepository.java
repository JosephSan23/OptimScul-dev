package backend.admission.infrastructure.persistence;

import backend.admission.infrastructure.persistence.entity.PostulacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PostulacionJpaRepository extends JpaRepository<PostulacionEntity, UUID> {
}
