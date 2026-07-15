package backend.community.application.usecase.EstudianteCrud;

import backend.community.application.port.EstudianteConsultaRepository;
import backend.community.application.port.EstudianteResumen;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarEstudiantesUseCase {
    private final EstudianteConsultaRepository repo;
    private final AutorizacionService auth;

    public ListarEstudiantesUseCase(EstudianteConsultaRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    public List<EstudianteResumen> ejecutar(UUID adminId) {
        return repo.listarPorInstitucion(auth.institucionConRol(adminId, "COORDINADOR_ACADEMICO"));
    }
}