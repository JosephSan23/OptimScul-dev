package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.AsistenciaClaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AsistenciaClaseJpaRepository extends JpaRepository<AsistenciaClaseEntity, UUID> {
    List<AsistenciaClaseEntity> findBySesionClaseId(UUID sesionClaseId);
    Optional<AsistenciaClaseEntity> findBySesionClaseIdAndEstudianteId(UUID sesionClaseId, UUID estudianteId);
}