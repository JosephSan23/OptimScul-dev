package backend.academic.application.usecase.grupo;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.application.port.GradoRepository;
import backend.academic.application.port.GrupoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.academic.domain.model.EstadoGrupo;
import backend.academic.domain.model.Grado;
import backend.academic.domain.model.Grupo;
import backend.academic.infrastructure.rest.dto.Grupo.GrupoRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearGrupoUseCase {
    private final GrupoRepository grupoRepo;
    private final GradoRepository gradoRepo;
    private final AnioLectivoRepository anioRepo;
    private final AutorizacionService auth;

    public CrearGrupoUseCase(GrupoRepository grupoRepo, GradoRepository gradoRepo,
            AnioLectivoRepository anioRepo, AutorizacionService auth) {
        this.grupoRepo = grupoRepo;
        this.gradoRepo = gradoRepo;
        this.anioRepo = anioRepo;
        this.auth = auth;
    }

    @Transactional
    public Grupo ejecutar(UUID usuarioId, UUID gradoId, GrupoRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarGruposPorGradoUseCase.ROLES);

        Grado grado = gradoRepo.findById(gradoId)
                .orElseThrow(() -> new RuntimeException("El grado no existe."));
        if (!inst.equals(grado.getInstitucionId()))
            throw new SecurityException("No puedes crear grupos en grados de otra institución.");

        AnioLectivo anio = anioRepo.findById(dto.getAnioLectivoId())
                .orElseThrow(() -> new RuntimeException("El año lectivo no existe."));
        if (!inst.equals(anio.getInstitucionId()))
            throw new SecurityException("El año lectivo no pertenece a tu institución.");

        if (grupoRepo.existsByInstitucionIdAndAnioLectivoIdAndCodigo(inst, dto.getAnioLectivoId(), dto.getCodigo()))
            throw new RuntimeException("Ya existe un grupo con el código " + dto.getCodigo() + " en ese año lectivo.");

        LocalDateTime ahora = LocalDateTime.now();
        Grupo g = new Grupo();
        g.setId(UUID.randomUUID());
        g.setInstitucionId(inst);
        g.setGradoId(gradoId);
        g.setAnioLectivoId(dto.getAnioLectivoId());
        g.setSedeId(dto.getSedeId());
        g.setJornadaId(dto.getJornadaId());
        g.setCodigo(dto.getCodigo());
        g.setNombre(dto.getNombre());
        g.setCupoMaximo(dto.getCupoMaximo());
        g.setObservaciones(dto.getObservaciones());
        g.setEstado(EstadoGrupo.ACTIVO);
        g.setCreatedAt(ahora);
        g.setUpdatedAt(ahora);
        return grupoRepo.save(g);
    }
}