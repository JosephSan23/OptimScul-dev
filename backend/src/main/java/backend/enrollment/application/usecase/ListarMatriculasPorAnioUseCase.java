package backend.enrollment.application.usecase;

import backend.enrollment.application.port.MatriculaConsultaRepository;
import backend.enrollment.application.port.MatriculaResumen;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarMatriculasPorAnioUseCase {
    private final MatriculaConsultaRepository consulta;
    private final AutorizacionService auth;

    public ListarMatriculasPorAnioUseCase(MatriculaConsultaRepository consulta, AutorizacionService auth) {
        this.consulta = consulta;
        this.auth = auth;
    }

    public List<MatriculaResumen> ejecutar(UUID usuarioId, UUID anioId) {
        UUID inst = auth.institucionConRol(usuarioId, CrearMatriculaUseCase.ROLES);
        return consulta.listarPorAnio(inst, anioId);
    }
}