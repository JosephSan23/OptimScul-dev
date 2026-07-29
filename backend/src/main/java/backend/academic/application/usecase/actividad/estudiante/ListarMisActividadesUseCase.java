package backend.academic.application.usecase.actividad.estudiante;

import backend.academic.application.port.MisActividadesConsultaRepository;
import backend.academic.application.port.MisActividadesConsultaRepository.MiActividadFila;
import backend.academic.application.service.ContextoEstudianteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarMisActividadesUseCase {

    private final ContextoEstudianteService contexto;
    private final MisActividadesConsultaRepository repo;

    public ListarMisActividadesUseCase(ContextoEstudianteService contexto, MisActividadesConsultaRepository repo) {
        this.contexto = contexto;
        this.repo = repo;
    }

    public List<MiActividadFila> ejecutar(UUID usuarioId, UUID anioId) {
        var ctx = contexto.resolver(usuarioId);
        return repo.listar(ctx.estudianteId(), anioId);
    }
}