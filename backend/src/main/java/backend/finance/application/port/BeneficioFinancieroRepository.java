package backend.finance.application.port;

import backend.finance.domain.model.BeneficioFinanciero;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeneficioFinancieroRepository {

    BeneficioFinanciero save(BeneficioFinanciero entity);
    Optional<BeneficioFinanciero> findById(UUID id);
    List<BeneficioFinanciero> findAll();
    void deleteById(UUID id);
}
