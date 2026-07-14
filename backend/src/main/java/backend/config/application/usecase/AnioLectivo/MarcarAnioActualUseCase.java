package backend.config.application.usecase.anioLectivo;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MarcarAnioActualUseCase {
    private final AnioLectivoRepository repo;
    private final AutorizacionService auth;
    public MarcarAnioActualUseCase(AnioLectivoRepository repo, AutorizacionService auth) { this.repo = repo; this.auth = auth; }
    @Transactional
    public void ejecutar(UUID adminId, UUID id) {
        UUID inst = auth.institucionDelAdmin(adminId);
        AnioLectivo objetivo = repo.findById(id).orElseThrow(() -> new RuntimeException("El año lectivo no existe."));
        if (!inst.equals(objetivo.getInstitucionId())) throw new SecurityException("No puedes cambiar años de otra institución.");
        LocalDateTime ahora = LocalDateTime.now();
        repo.findByInstitucionId(inst).forEach(a -> {
            if (Boolean.TRUE.equals(a.getEsActual()) && !a.getId().equals(id)) {
                a.setEsActual(false); a.setUpdatedAt(ahora); repo.save(a);
            }
        });
        objetivo.setEsActual(true); objetivo.setUpdatedAt(ahora); repo.save(objetivo);
    }
}