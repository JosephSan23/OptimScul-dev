package backend.enrollment.infrastructure.persistence;

import backend.enrollment.domain.model.EstadoMatricula;
import backend.enrollment.infrastructure.persistence.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface MatriculaJpaRepository extends JpaRepository<MatriculaEntity, UUID> {
    @Query(value = """
            SELECT grupo_id AS grupoId, count(*) AS total
            FROM optimscul.matricula
            WHERE institucion_id = CAST(:institucionId AS uuid)
              AND estado = 'MATRICULADO'
              AND grupo_id IS NOT NULL
            GROUP BY grupo_id
            """, nativeQuery = true)
    List<ConteoPorGrupo> contarMatriculadosPorGrupo(
            @Param("institucionId") String institucionId);

    interface ConteoPorGrupo {
        java.util.UUID getGrupoId();
        Long getTotal();
    }

    Optional<MatriculaEntity> findByInstitucionIdAndEstudianteIdAndAnioLectivoId(UUID institucionId, UUID estudianteId,
            UUID anioLectivoId);

    long countByGrupoIdAndEstado(UUID grupoId, EstadoMatricula estado);
}