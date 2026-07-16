package backend.academic.application.usecase.grupo;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.application.port.GrupoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.academic.domain.model.Grupo;
import backend.academic.infrastructure.rest.dto.GrupoRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarGrupoUseCase {
    private final GrupoRepository grupoRepo;
    private final AnioLectivoRepository anioRepo;
    private final AutorizacionService auth;

    public EditarGrupoUseCase(GrupoRepository grupoRepo, AnioLectivoRepository anioRepo, AutorizacionService auth) {
        this.grupoRepo = grupoRepo;
        this.anioRepo = anioRepo;
        this.auth = auth;
    }

    @Transactional
    public Grupo ejecutar(UUID usuarioId, UUID grupoId, GrupoRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarGruposPorGradoUseCase.ROLES);

        Grupo g = grupoRepo.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("El grupo no existe."));
        if (!inst.equals(g.getInstitucionId()))
            throw new SecurityException("No puedes editar grupos de otra institución.");

        AnioLectivo anio = anioRepo.findById(dto.getAnioLectivoId())
                .orElseThrow(() -> new RuntimeException("El año lectivo no existe."));
        if (!inst.equals(anio.getInstitucionId()))
            throw new SecurityException("El año lectivo no pertenece a tu institución.");

        boolean cambioClave = !g.getCodigo().equalsIgnoreCase(dto.getCodigo())
                || !g.getAnioLectivoId().equals(dto.getAnioLectivoId());
        if (cambioClave && grupoRepo.existsByInstitucionIdAndAnioLectivoIdAndCodigo(inst, dto.getAnioLectivoId(),
                dto.getCodigo()))
            throw new RuntimeException("Ya existe un grupo con el código " + dto.getCodigo() + " en ese año lectivo.");

        g.setAnioLectivoId(dto.getAnioLectivoId());
        g.setSedeId(dto.getSedeId());
        g.setJornadaId(dto.getJornadaId());
        g.setCodigo(dto.getCodigo());
        g.setNombre(dto.getNombre());
        g.setCupoMaximo(dto.getCupoMaximo());
        g.setObservaciones(dto.getObservaciones());
        g.setUpdatedAt(LocalDateTime.now());
        return grupoRepo.save(g);
    }
}