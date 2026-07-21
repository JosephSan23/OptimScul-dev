package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.CargaAcademicaEntity;
import backend.academic.domain.model.EstadoCargaAcademica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CargaAcademicaJpaRepository extends JpaRepository<CargaAcademicaEntity, UUID> {
    boolean existsByInstitucionIdAndAnioLectivoIdAndGrupoIdAndAsignaturaIdAndEstado(
            UUID institucionId, UUID anioLectivoId, UUID grupoId, UUID asignaturaId,
            EstadoCargaAcademica estado);
}
