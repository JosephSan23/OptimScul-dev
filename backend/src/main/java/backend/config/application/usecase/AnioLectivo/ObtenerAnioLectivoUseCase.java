package backend.config.application.usecase.AnioLectivo;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerAnioLectivoUseCase {
    private final AnioLectivoRepository repo;
    private final AutorizacionService auth;

    public ObtenerAnioLectivoUseCase(AnioLectivoRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    public AnioLectivo ejecutar(UUID adminId, UUID id) {
        UUID inst = auth.institucionDelAdmin(adminId);
        AnioLectivo a = repo.findById(id).orElseThrow(() -> new RuntimeException("El año lectivo no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("No puedes ver años de otra institución.");
        return a;
    }
}