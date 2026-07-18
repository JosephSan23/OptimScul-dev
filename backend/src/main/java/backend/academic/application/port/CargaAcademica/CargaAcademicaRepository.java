package backend.academic.application.port.CargaAcademica;

import backend.academic.domain.model.CargaAcademica;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CargaAcademicaRepository {

    CargaAcademica save(CargaAcademica entity);
    Optional<CargaAcademica> findById(UUID id);
    List<CargaAcademica> findAll();
    void deleteById(UUID id);
    boolean existsActiva(UUID institucionId, UUID anioLectivoId, UUID grupoId, UUID asignaturaId);
}
