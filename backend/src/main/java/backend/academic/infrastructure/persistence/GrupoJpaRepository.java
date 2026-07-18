package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.GrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GrupoJpaRepository extends JpaRepository<GrupoEntity, UUID> {
    List<GrupoEntity> findByGradoIdOrderByCodigoAsc(UUID gradoId);
    boolean existsByInstitucionIdAndAnioLectivoIdAndCodigoIgnoreCase(UUID institucionId, UUID anioLectivoId, String codigo);

    @Query("select g.gradoId as gradoId, count(g) as total " +
           "from GrupoEntity g where g.institucionId = :institucionId group by g.gradoId")
    List<ConteoPorGrado> contarPorGrado(@Param("institucionId") UUID institucionId);

    interface ConteoPorGrado {
        UUID getGradoId();
        Long getTotal();
    }

    List<GrupoEntity> findByInstitucionIdAndAnioLectivoIdOrderByCodigoAsc(UUID institucionId, UUID anioLectivoId);

}