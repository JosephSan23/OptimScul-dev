package backend.academic.application.usecase.horario;

import backend.academic.application.port.HorarioCargaRepository;
import backend.academic.domain.model.HorarioCarga;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerHorarioUseCase {
    private final HorarioCargaRepository repo;
    private final AutorizacionService auth;

    public ObtenerHorarioUseCase(HorarioCargaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    public HorarioCarga ejecutar(UUID usuarioId, UUID horarioId) {
        UUID inst = auth.institucionConRol(usuarioId, ListarHorariosPorGrupoUseCase.ROLES);
        HorarioCarga h = repo.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("El horario no existe."));
        if (!inst.equals(h.getInstitucionId()))
            throw new SecurityException("No puedes ver horarios de otra institución.");
        return h;
    }
}