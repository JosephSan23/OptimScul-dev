package backend.academic.application.port;

import backend.academic.domain.model.SesionClase;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

public interface SesionClaseRepository {

    SesionClase save(SesionClase entity);
    Optional<SesionClase> findById(UUID id);
    List<SesionClase> findAll();
    void deleteById(UUID id);
    Optional<SesionClase> findByCargaAcademicaIdAndFecha(UUID cargaAcademicaId, LocalDate fecha);
}
