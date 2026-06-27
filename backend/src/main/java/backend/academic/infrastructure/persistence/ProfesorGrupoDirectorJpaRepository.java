package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.ProfesorGrupoDirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProfesorGrupoDirectorJpaRepository extends JpaRepository<ProfesorGrupoDirectorEntity, UUID> {
}
