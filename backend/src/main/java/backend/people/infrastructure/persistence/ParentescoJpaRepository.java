package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.ParentescoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ParentescoJpaRepository extends JpaRepository<ParentescoEntity, UUID> {
}
