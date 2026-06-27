package backend.academic.application.port;

import backend.academic.domain.model.EscalaValorativa;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EscalaValorativaRepository {

    EscalaValorativa save(EscalaValorativa entity);
    Optional<EscalaValorativa> findById(UUID id);
    List<EscalaValorativa> findAll();
    void deleteById(UUID id);
}
