package backend.admission.application.port;

import backend.admission.domain.model.PostulacionPersona;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostulacionPersonaRepository {

    PostulacionPersona save(PostulacionPersona entity);
    Optional<PostulacionPersona> findById(UUID id);
    List<PostulacionPersona> findAll();
    void deleteById(UUID id);
}
