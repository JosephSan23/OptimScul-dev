package backend.onboarding.infrastructure.persistence;

import backend.onboarding.infrastructure.persistence.entity.SolicitudInstitucionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface SolicitudInstitucionJpaRepository extends JpaRepository<SolicitudInstitucionEntity, UUID> {
    boolean existsByEnviadaPorUsuarioIdAndEstado(UUID enviadaPorUsuarioId, String estado);
    List<SolicitudInstitucionEntity> findByEnviadaPorUsuarioId(UUID enviadaPorUsuarioId);
}