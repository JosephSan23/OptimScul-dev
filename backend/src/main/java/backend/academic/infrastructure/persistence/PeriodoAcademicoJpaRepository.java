package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.PeriodoAcademicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PeriodoAcademicoJpaRepository extends JpaRepository<PeriodoAcademicoEntity, UUID> {
    java.util.List<PeriodoAcademicoEntity> findByAnioLectivoId(UUID anioLectivoId);
}
