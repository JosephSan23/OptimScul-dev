package backend.enrollment.infrastructure.persistence;

import backend.enrollment.domain.model.EstadoMatricula;
import backend.enrollment.infrastructure.persistence.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface MatriculaJpaRepository extends JpaRepository<MatriculaEntity, UUID> {
    Optional<MatriculaEntity> findByInstitucionIdAndEstudianteIdAndAnioLectivoId(UUID institucionId, UUID estudianteId,
            UUID anioLectivoId);

    long countByGrupoIdAndEstado(UUID grupoId, EstadoMatricula estado);
}