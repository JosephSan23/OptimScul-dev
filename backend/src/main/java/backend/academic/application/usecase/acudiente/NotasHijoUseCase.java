package backend.academic.application.usecase.acudiente;

import backend.academic.application.service.BoletinService;
import backend.academic.application.service.ContextoAcudienteService;
import backend.people.application.port.EstudianteAcudienteRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class NotasHijoUseCase {
    private final ContextoAcudienteService contexto;
    private final EstudianteAcudienteRepository vinculoRepo;
    private final BoletinService boletin;

    public NotasHijoUseCase(ContextoAcudienteService contexto, EstudianteAcudienteRepository vinculoRepo,
            BoletinService boletin) {
        this.contexto = contexto;
        this.vinculoRepo = vinculoRepo;
        this.boletin = boletin;
    }

    public BoletinService.Vista ejecutar(UUID usuarioId, UUID estudianteId, UUID anioId, UUID periodoId) {
        var ctx = contexto.resolver(usuarioId);
        if (!vinculoRepo.existsByEstudianteIdAndAcudienteId(estudianteId, ctx.acudienteId()))
            throw new SecurityException("Ese estudiante no está vinculado a ti.");
        return boletin.calcular(estudianteId, anioId, periodoId);
    }
}