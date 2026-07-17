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
public class EditarAsignaturaUseCase {
    private final AsignaturaRepository asignaturaRepo;
    private final AreaAcademicaRepository areaRepo;
    private final AutorizacionService auth;

    public EditarAsignaturaUseCase(AsignaturaRepository asignaturaRepo, AreaAcademicaRepository areaRepo,
                                   AutorizacionService auth) {
        this.asignaturaRepo = asignaturaRepo; this.areaRepo = areaRepo; this.auth = auth;
    }

    @Transactional
    public Asignatura ejecutar(UUID usuarioId, UUID asignaturaId, AsignaturaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarAsignaturasUseCase.ROLES);

        Asignatura a = asignaturaRepo.findById(asignaturaId)
                .orElseThrow(() -> new RuntimeException("La asignatura no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("No puedes editar asignaturas de otra institución.");

        if (dto.getAreaId() != null) {
            AreaAcademica area = areaRepo.findById(dto.getAreaId())
                    .orElseThrow(() -> new RuntimeException("El área no existe."));
            if (!inst.equals(area.getInstitucionId()))
                throw new SecurityException("El área no pertenece a tu institución.");
        }

        if (!a.getCodigo().equalsIgnoreCase(dto.getCodigo())
                && asignaturaRepo.existsByInstitucionIdAndCodigo(inst, dto.getCodigo()))
            throw new RuntimeException("Ya existe una asignatura con el código " + dto.getCodigo() + ".");

        a.setAreaId(dto.getAreaId());
        a.setCodigo(dto.getCodigo());
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setIntensidadHorariaSemanal(dto.getIntensidadHorariaSemanal());
        if (dto.getRequiereCalificacion() != null) a.setRequiereCalificacion(dto.getRequiereCalificacion());
        if (dto.getRequiereRecuperacion() != null) a.setRequiereRecuperacion(dto.getRequiereRecuperacion());
        if (dto.getEsComportamiento() != null)     a.setEsComportamiento(dto.getEsComportamiento());
        a.setUpdatedBy(usuarioId);
        a.setUpdatedAt(LocalDateTime.now());
        return asignaturaRepo.save(a);
    }
}