package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.InstitucionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InstitucionJpaRepository extends JpaRepository<InstitucionEntity, UUID> {
    boolean existsByCodigo(String codigo);
}

