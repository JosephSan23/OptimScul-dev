package backend.config.application.usecase.anioLectivo;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarAniosLectivosUseCase {
    private final AnioLectivoRepository repo;
    private final AutorizacionService auth;

    public ListarAniosLectivosUseCase(AnioLectivoRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    public List<AnioLectivo> ejecutar(UUID adminId) {
        return repo.findByInstitucionId(auth.institucionConRol(adminId, "ADMIN_INSTITUCION", "COORDINADOR_ACADEMICO"));
    }
}