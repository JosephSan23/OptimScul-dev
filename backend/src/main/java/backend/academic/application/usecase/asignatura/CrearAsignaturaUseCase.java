package backend.academic.application.usecase.asignatura;

import backend.academic.application.port.AreaAcademicaRepository;
import backend.academic.application.port.AsignaturaRepository;
import backend.academic.domain.model.AreaAcademica;
import backend.academic.domain.model.Asignatura;
import backend.academic.infrastructure.rest.dto.Asignatura.AsignaturaRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearAsignaturaUseCase {
    private final AsignaturaRepository asignaturaRepo;
    private final AreaAcademicaRepository areaRepo;
    private final AutorizacionService auth;

    public CrearAsignaturaUseCase(AsignaturaRepository asignaturaRepo, AreaAcademicaRepository areaRepo,
                                  AutorizacionService auth) {
        this.asignaturaRepo = asignaturaRepo; this.areaRepo = areaRepo; this.auth = auth;
    }

    @Transactional
    public Asignatura ejecutar(UUID usuarioId, AsignaturaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarAsignaturasUseCase.ROLES);

        if (dto.getAreaId() != null) validarArea(inst, dto.getAreaId());

        if (asignaturaRepo.existsByInstitucionIdAndCodigo(inst, dto.getCodigo()))
            throw new RuntimeException("Ya existe una asignatura con el código " + dto.getCodigo() + ".");

        LocalDateTime ahora = LocalDateTime.now();
        Asignatura a = new Asignatura();
        a.setId(UUID.randomUUID());
        a.setInstitucionId(inst);
        a.setAreaId(dto.getAreaId());
        a.setCodigo(dto.getCodigo());
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setIntensidadHorariaSemanal(dto.getIntensidadHorariaSemanal());
        a.setRequiereCalificacion(dto.getRequiereCalificacion() != null ? dto.getRequiereCalificacion() : true);
        a.setRequiereRecuperacion(dto.getRequiereRecuperacion() != null ? dto.getRequiereRecuperacion() : true);
        a.setEsComportamiento(dto.getEsComportamiento() != null ? dto.getEsComportamiento() : false);
        a.setActiva(true);
        a.setCreatedBy(usuarioId);
        a.setUpdatedBy(usuarioId);
        a.setCreatedAt(ahora);
        a.setUpdatedAt(ahora);
        return asignaturaRepo.save(a);
    }

    private void validarArea(UUID inst, UUID areaId) {
        AreaAcademica area = areaRepo.findById(areaId)
                .orElseThrow(() -> new RuntimeException("El área no existe."));
        if (!inst.equals(area.getInstitucionId()))
            throw new SecurityException("El área no pertenece a tu institución.");
    }
}