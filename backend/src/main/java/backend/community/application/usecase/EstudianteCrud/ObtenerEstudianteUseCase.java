package backend.community.application.usecase.EstudianteCrud;

import backend.community.application.EstudianteDetalle;
import backend.people.application.port.EstudianteRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Estudiante;
import backend.people.domain.model.Persona;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerEstudianteUseCase {
    private final EstudianteRepository estudianteRepository;
    private final PersonaRepository personaRepository;
    private final AutorizacionService auth;

    public ObtenerEstudianteUseCase(EstudianteRepository estudianteRepository, PersonaRepository personaRepository,
            AutorizacionService auth) {
        this.estudianteRepository = estudianteRepository;
        this.personaRepository = personaRepository;
        this.auth = auth;
    }

    public EstudianteDetalle ejecutar(UUID coordId, UUID estudianteId) {
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");
        Estudiante e = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        if (!inst.equals(e.getInstitucionId()))
            throw new SecurityException("No puedes ver estudiantes de otra institución.");
        Persona p = personaRepository.findById(e.getPersonaId())
                .orElseThrow(() -> new RuntimeException("No se encontró la persona."));
        return new EstudianteDetalle(
                e.getId(), e.getCodigoEstudiante(), e.getEstado() != null ? e.getEstado().name() : null,
                e.getFechaIngreso(), e.getObservaciones(),
                p.getTipoDocumento() != null ? p.getTipoDocumento().name() : null, p.getNumeroDocumento(),
                p.getPrimerNombre(), p.getSegundoNombre(), p.getPrimerApellido(), p.getSegundoApellido(),
                p.getCorreo(), p.getTelefono(), p.getFechaNacimiento(), p.getDireccion(), p.getCiudad());
    }
}
