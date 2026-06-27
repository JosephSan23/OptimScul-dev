package backend.admission.application.port;

import backend.admission.domain.model.PostulacionAcudiente;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostulacionAcudienteRepository {

    PostulacionAcudiente save(PostulacionAcudiente entity);
    Optional<PostulacionAcudiente> findById(UUID id);
    List<PostulacionAcudiente> findAll();
    void deleteById(UUID id);
}
