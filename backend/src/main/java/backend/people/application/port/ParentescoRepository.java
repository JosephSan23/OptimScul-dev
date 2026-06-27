package backend.people.application.port;

import backend.people.domain.model.Parentesco;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParentescoRepository {

    Parentesco save(Parentesco entity);
    Optional<Parentesco> findById(UUID id);
    List<Parentesco> findAll();
    void deleteById(UUID id);
}
