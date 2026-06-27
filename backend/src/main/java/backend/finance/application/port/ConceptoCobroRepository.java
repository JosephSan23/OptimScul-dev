package backend.finance.application.port;

import backend.finance.domain.model.ConceptoCobro;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConceptoCobroRepository {

    ConceptoCobro save(ConceptoCobro entity);
    Optional<ConceptoCobro> findById(UUID id);
    List<ConceptoCobro> findAll();
    void deleteById(UUID id);
}
