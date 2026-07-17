package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.AreaAcademicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AreaAcademicaJpaRepository extends JpaRepository<AreaAcademicaEntity, UUID> {
    List<AreaAcademicaEntity> findByInstitucionIdOrderByNombreAsc(UUID institucionId);
    boolean existsByInstitucionIdAndCodigoIgnoreCase(UUID institucionId, String codigo);
}