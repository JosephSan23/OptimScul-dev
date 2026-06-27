package backend.finance.application.port;

import backend.finance.domain.model.CuentaCobroBeneficio;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CuentaCobroBeneficioRepository {

    CuentaCobroBeneficio save(CuentaCobroBeneficio entity);
    Optional<CuentaCobroBeneficio> findById(UUID id);
    List<CuentaCobroBeneficio> findAll();
    void deleteById(UUID id);
}
