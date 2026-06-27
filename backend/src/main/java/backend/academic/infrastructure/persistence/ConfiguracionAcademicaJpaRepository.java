package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.ConfiguracionAcademicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ConfiguracionAcademicaJpaRepository extends JpaRepository<ConfiguracionAcademicaEntity, UUID> {
}
