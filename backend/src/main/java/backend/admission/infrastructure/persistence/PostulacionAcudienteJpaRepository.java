package backend.admission.infrastructure.persistence;

import backend.admission.infrastructure.persistence.entity.PostulacionAcudienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PostulacionAcudienteJpaRepository extends JpaRepository<PostulacionAcudienteEntity, UUID> {
}
