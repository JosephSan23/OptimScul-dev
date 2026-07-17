package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.AsignaturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface AsignaturaJpaRepository extends JpaRepository<AsignaturaEntity, UUID> {
    List<AsignaturaEntity> findByInstitucionIdOrderByNombreAsc(UUID institucionId);
    boolean existsByInstitucionIdAndCodigoIgnoreCase(UUID institucionId, String codigo);

    @Query("select a.areaId as areaId, count(a) as total " +
           "from AsignaturaEntity a where a.institucionId = :institucionId and a.areaId is not null group by a.areaId")
    List<ConteoPorArea> contarPorArea(@Param("institucionId") UUID institucionId);

    interface ConteoPorArea {
        UUID getAreaId();
        Long getTotal();
    }
}