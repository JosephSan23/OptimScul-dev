package backend.academic.application.usecase.acudiente;

import backend.academic.application.service.ContextoAcudienteService;
import backend.academic.application.port.Acudiente.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarHijosUseCase {
    private final ContextoAcudienteService contexto;
    private final HijosConsultaRepository consulta;

    public ListarHijosUseCase(ContextoAcudienteService contexto, HijosConsultaRepository consulta) {
        this.contexto = contexto; this.consulta = consulta;
    }

    public List<HijoResumen> ejecutar(UUID usuarioId) {
        var ctx = contexto.resolver(usuarioId);
        return consulta.hijosDeAcudiente(ctx.acudienteId());
    }
}