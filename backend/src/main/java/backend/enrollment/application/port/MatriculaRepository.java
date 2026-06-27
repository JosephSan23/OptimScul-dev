package backend.enrollment.application.port;

import backend.enrollment.domain.model.Matricula;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatriculaRepository {

    Matricula save(Matricula entity);
    Optional<Matricula> findById(UUID id);
    List<Matricula> findAll();
    void deleteById(UUID id);
}
