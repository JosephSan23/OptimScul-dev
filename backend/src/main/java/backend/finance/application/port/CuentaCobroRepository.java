package backend.finance.application.port;

import backend.finance.domain.model.CuentaCobro;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CuentaCobroRepository {

    CuentaCobro save(CuentaCobro entity);
    Optional<CuentaCobro> findById(UUID id);
    List<CuentaCobro> findAll();
    void deleteById(UUID id);
}
