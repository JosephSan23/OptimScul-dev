package backend.academic.application.usecase.horario;

import backend.academic.application.port.GrupoRepository;
import backend.academic.application.port.Horario.HorarioConsultaRepository;
import backend.academic.application.port.Horario.HorarioResumen;
import backend.academic.domain.model.Grupo;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarHorariosPorGrupoUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    private final HorarioConsultaRepository consulta;
    private final GrupoRepository grupoRepo;
    private final AutorizacionService auth;

    public ListarHorariosPorGrupoUseCase(HorarioConsultaRepository consulta, GrupoRepository grupoRepo,
            AutorizacionService auth) {
        this.consulta = consulta;
        this.grupoRepo = grupoRepo;
        this.auth = auth;
    }

    public List<HorarioResumen> ejecutar(UUID usuarioId, UUID grupoId) {
        UUID inst = auth.institucionConRol(usuarioId, ROLES);
        Grupo g = grupoRepo.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("El grupo no existe."));
        if (!inst.equals(g.getInstitucionId()))
            throw new SecurityException("El grupo no pertenece a tu institución.");
        return consulta.listarPorGrupo(inst, grupoId);
    }
}