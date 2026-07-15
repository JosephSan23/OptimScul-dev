package backend.community.application.usecase.EstudianteCrud;

import backend.community.infrastructure.rest.dto.EstudianteRequestDto;
import backend.onboarding.application.AltaUsuarioInstitucionService;
import backend.people.application.port.EstudianteRepository;
import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.EstadoEstudiante;
import backend.people.domain.model.Estudiante;
import backend.people.domain.model.Institucion;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearEstudianteUseCase {

    private final AltaUsuarioInstitucionService altaUsuario;
    private final EstudianteRepository estudianteRepository;
    private final InstitucionRepository institucionRepository;
    private final AutorizacionService auth;

    public CrearEstudianteUseCase(AltaUsuarioInstitucionService altaUsuario, EstudianteRepository estudianteRepository,
            InstitucionRepository institucionRepository, AutorizacionService auth) {
        this.altaUsuario = altaUsuario;
        this.estudianteRepository = estudianteRepository;
        this.institucionRepository = institucionRepository;
        this.auth = auth;
    }

    public record Resultado(String username, String codigoEstudiante) {
    }

    @Transactional
    public Resultado ejecutar(UUID adminId, EstudianteRequestDto dto) {
        UUID inst = auth.institucionConRol(adminId, "COORDINADOR_ACADEMICO");

        // 1. Cuenta (persona + usuario ESTUDIANTE + rol + vínculo)
        AltaUsuarioInstitucionService.Resultado cuenta = altaUsuario.provisionar(
                inst, "ESTUDIANTE", dto.getTipoDocumento(), dto.getNumeroDocumento(),
                dto.getPrimerNombre(), dto.getPrimerApellido(), dto.getCorreo());

        // 2. Registro estudiante con código automático
        Institucion institucion = institucionRepository.findById(inst)
                .orElseThrow(() -> new RuntimeException("La institución no existe."));
        LocalDateTime ahora = LocalDateTime.now();

        Estudiante e = new Estudiante();
        e.setId(UUID.randomUUID());
        e.setInstitucionId(inst);
        e.setPersonaId(cuenta.persona().getId());
        e.setCodigoEstudiante(generarCodigo(institucion, inst));
        e.setFechaIngreso(dto.getFechaIngreso() != null ? dto.getFechaIngreso() : LocalDate.now());
        e.setEstado(EstadoEstudiante.ACTIVO);
        e.setObservaciones(dto.getObservaciones());
        e.setCreatedAt(ahora);
        e.setUpdatedAt(ahora);
        estudianteRepository.save(e);

        return new Resultado(cuenta.usuario().getUsername(), e.getCodigoEstudiante());
    }

    // Código: {códigoColegio}-{consecutivo 4 dígitos}
    private String generarCodigo(Institucion institucion, UUID institucionId) {
        long consecutivo = estudianteRepository.findByInstitucionId(institucionId).size() + 1;
        String base = (institucion.getCodigo() != null && !institucion.getCodigo().isBlank())
                ? institucion.getCodigo()
                : "EST";
        return base + "-" + String.format("%04d", consecutivo);
    }
}