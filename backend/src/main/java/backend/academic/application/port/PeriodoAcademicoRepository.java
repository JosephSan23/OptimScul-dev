package backend.academic.application.port;

import backend.academic.domain.model.PeriodoAcademico;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeriodoAcademicoRepository {

    PeriodoAcademico save(PeriodoAcademico entity);
    Optional<PeriodoAcademico> findById(UUID id);
    List<PeriodoAcademico> findAll();
    void deleteById(UUID id);
    List<PeriodoAcademico> findByAnioLectivoId(UUID anioLectivoId);
}
