package backend.academic.application.usecase.estudiante;

import backend.academic.application.service.BoletinService;
import backend.academic.application.service.ContextoEstudianteService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class MisNotasUseCase {
    private final ContextoEstudianteService contexto;
    private final BoletinService boletin;

    public MisNotasUseCase(ContextoEstudianteService contexto, BoletinService boletin) {
        this.contexto = contexto;
        this.boletin = boletin;
    }

    public BoletinService.Vista ejecutar(UUID usuarioId, UUID anioId, UUID periodoId) {
        var ctx = contexto.resolver(usuarioId);
        return boletin.calcular(ctx.estudianteId(), anioId, periodoId);
    }
}