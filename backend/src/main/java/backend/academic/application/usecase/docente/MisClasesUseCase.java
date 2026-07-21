package backend.academic.application.usecase.docente;

import backend.academic.application.port.DocenteConsultaRepository;
import backend.academic.application.port.MiClaseResumen;
import backend.academic.application.service.ContextoDocenteService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class MisClasesUseCase {
    private final DocenteConsultaRepository consulta;
    private final ContextoDocenteService contexto;

    public MisClasesUseCase(DocenteConsultaRepository consulta, ContextoDocenteService contexto) {
        this.consulta = consulta; this.contexto = contexto;
    }

    public List<MiClaseResumen> ejecutar(UUID usuarioId, UUID anioId) {
        var ctx = contexto.resolver(usuarioId);
        return consulta.misClases(ctx.profesorId(), anioId);
    }
}