package backend.academic.application.usecase.area;

import backend.academic.application.port.AreaAcademicaRepository;
import backend.academic.domain.model.AreaAcademica;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerAreaUseCase {
    private final AreaAcademicaRepository repo;
    private final AutorizacionService auth;

    public ObtenerAreaUseCase(AreaAcademicaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    public AreaAcademica ejecutar(UUID usuarioId, UUID areaId) {
        UUID inst = auth.institucionConRol(usuarioId, ListarAreasUseCase.ROLES);
        AreaAcademica a = repo.findById(areaId)
                .orElseThrow(() -> new RuntimeException("El área no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("No puedes ver áreas de otra institución.");
        return a;
    }
}