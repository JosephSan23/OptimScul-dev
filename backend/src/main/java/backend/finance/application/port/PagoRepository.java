package backend.finance.application.port;

import backend.finance.domain.model.Pago;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagoRepository {

    Pago save(Pago entity);
    Optional<Pago> findById(UUID id);
    List<Pago> findAll();
    void deleteById(UUID id);
}
