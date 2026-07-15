package backend.community.application.usecase.AcudienteCrud;

import backend.community.application.AcudienteDetalle;
import backend.people.application.port.AcudienteRepository;
import backend.people.application.port.EstudianteAcudienteRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Acudiente;
import backend.people.domain.model.EstudianteAcudiente;
import backend.people.domain.model.Persona;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerAcudienteUseCase {
    private final EstudianteAcudienteRepository vinculoRepository;
    private final AcudienteRepository acudienteRepository;
    private final PersonaRepository personaRepository;
    private final AutorizacionService auth;

    public ObtenerAcudienteUseCase(EstudianteAcudienteRepository vinculoRepository,
            AcudienteRepository acudienteRepository,
            PersonaRepository personaRepository, AutorizacionService auth) {
        this.vinculoRepository = vinculoRepository;
        this.acudienteRepository = acudienteRepository;
        this.personaRepository = personaRepository;
        this.auth = auth;
    }

    public AcudienteDetalle ejecutar(UUID coordId, UUID vinculoId) {
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");
        EstudianteAcudiente v = vinculoRepository.findById(vinculoId)
                .orElseThrow(() -> new RuntimeException("El vínculo no existe."));
        Acudiente a = acudienteRepository.findById(v.getAcudienteId())
                .orElseThrow(() -> new RuntimeException("El acudiente no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("Acudiente de otra institución.");
        Persona p = personaRepository.findById(a.getPersonaId())
                .orElseThrow(() -> new RuntimeException("No se encontró la persona."));
        return new AcudienteDetalle(
                v.getId(), a.getId(),
                v.getParentesco() != null ? v.getParentesco().name() : null,
                v.getEsPrincipal(), v.getAutorizadoRecogida(), v.getAutorizadoInfoAcademica(),
                a.getEstado() != null ? a.getEstado().name() : null, a.getOcupacion(), a.getEmpresa(),
                p.getTipoDocumento() != null ? p.getTipoDocumento().name() : null, p.getNumeroDocumento(),
                p.getPrimerNombre(), p.getSegundoNombre(), p.getPrimerApellido(), p.getSegundoApellido(),
                p.getCorreo(), p.getTelefono());
    }
}