package backend.community.application.usecase.AcudienteCrud;

import backend.people.application.port.AcudienteRepository;
import backend.people.application.port.EstudianteAcudienteRepository;
import backend.people.domain.model.Acudiente;
import backend.people.domain.model.EstudianteAcudiente;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class DesvincularAcudienteUseCase {
    private final EstudianteAcudienteRepository vinculoRepository;
    private final AcudienteRepository acudienteRepository;
    private final AutorizacionService auth;

    public DesvincularAcudienteUseCase(EstudianteAcudienteRepository vinculoRepository,
            AcudienteRepository acudienteRepository, AutorizacionService auth) {
        this.vinculoRepository = vinculoRepository;
        this.acudienteRepository = acudienteRepository;
        this.auth = auth;
    }

    @Transactional
    public void ejecutar(UUID coordId, UUID vinculoId) {
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");
        EstudianteAcudiente v = vinculoRepository.findById(vinculoId)
                .orElseThrow(() -> new RuntimeException("El vínculo no existe."));
        Acudiente a = acudienteRepository.findById(v.getAcudienteId())
                .orElseThrow(() -> new RuntimeException("El acudiente no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("Acudiente de otra institución.");
        vinculoRepository.deleteById(vinculoId);
        // La cuenta y el registro del acudiente quedan (puede estar vinculado a otros
        // estudiantes).
    }
}