package backend.config.application.usecase.periodoAcademico;

import backend.academic.application.port.PeriodoAcademicoRepository;
import backend.academic.domain.model.PeriodoAcademico;
import backend.security.application.AutorizacionService;
import backend.notification.application.service.NotificacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class HabilitarBoletinUseCase {
    private final PeriodoAcademicoRepository repo;
    private final AutorizacionService auth;
    private final NotificacionService notificacionService;

    public HabilitarBoletinUseCase(PeriodoAcademicoRepository repo, AutorizacionService auth, NotificacionService notificacionService) {
        this.repo = repo;
        this.auth = auth;
        this.notificacionService = notificacionService;
    }

    @Transactional
    public void ejecutar(UUID usuarioId, UUID periodoId, boolean habilitado) {
        UUID inst = auth.institucionConRol(usuarioId, "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION");
        PeriodoAcademico p = repo.findById(periodoId)
                .orElseThrow(() -> new RuntimeException("El periodo no existe."));
        if (!inst.equals(p.getInstitucionId()))
            throw new SecurityException("Ese periodo no pertenece a tu institución.");
        p.setBoletinHabilitado(habilitado);
        p.setUpdatedAt(LocalDateTime.now());
        repo.save(p);

        if (habilitado) {                          
            notificacionService.notificarBoletinDisponible(
                    p.getInstitucionId(), p.getAnioLectivoId(),
                    p.getId(), p.getNombre(), usuarioId);
        }
    }

}