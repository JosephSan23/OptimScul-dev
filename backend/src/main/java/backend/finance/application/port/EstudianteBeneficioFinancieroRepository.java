package backend.finance.application.port;

import backend.finance.domain.model.EstudianteBeneficioFinanciero;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstudianteBeneficioFinancieroRepository {

    EstudianteBeneficioFinanciero save(EstudianteBeneficioFinanciero entity);
    Optional<EstudianteBeneficioFinanciero> findById(UUID id);
    List<EstudianteBeneficioFinanciero> findAll();
    void deleteById(UUID id);
}
