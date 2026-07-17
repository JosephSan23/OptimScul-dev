package backend.academic.application.usecase.area;

import backend.academic.application.port.AreaAcademicaRepository;
import backend.academic.domain.model.AreaAcademica;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoAreaUseCase {
    private final AreaAcademicaRepository repo;
    private final AutorizacionService auth;

    public CambiarEstadoAreaUseCase(AreaAcademicaRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    @Transactional
    public void activar(UUID u, UUID id) {
        cambiar(u, id, true);
    }

    @Transactional
    public void inactivar(UUID u, UUID id) {
        cambiar(u, id, false);
    }

    private void cambiar(UUID usuarioId, UUID areaId, boolean activa) {
        UUID inst = auth.institucionConRol(usuarioId, ListarAreasUseCase.ROLES);
        AreaAcademica a = repo.findById(areaId)
                .orElseThrow(() -> new RuntimeException("El área no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("No puedes cambiar áreas de otra institución.");
        a.setActiva(activa);
        a.setUpdatedAt(LocalDateTime.now());
        repo.save(a);
    }
}