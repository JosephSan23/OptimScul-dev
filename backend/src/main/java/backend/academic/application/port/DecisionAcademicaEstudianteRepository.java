package backend.academic.application.port;

import backend.academic.domain.model.DecisionAcademicaEstudiante;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DecisionAcademicaEstudianteRepository {

    DecisionAcademicaEstudiante save(DecisionAcademicaEstudiante entity);
    Optional<DecisionAcademicaEstudiante> findById(UUID id);
    List<DecisionAcademicaEstudiante> findAll();
    void deleteById(UUID id);
}
