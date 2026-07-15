package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.EstudianteAcudienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EstudianteAcudienteJpaRepository extends JpaRepository<EstudianteAcudienteEntity, UUID> {
    java.util.List<EstudianteAcudienteEntity> findByEstudianteId(UUID estudianteId);
    boolean existsByEstudianteIdAndAcudienteId(UUID estudianteId, UUID acudienteId);
}
