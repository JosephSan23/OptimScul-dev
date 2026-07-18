package backend.academic.application.usecase.carga;

import backend.people.application.port.profesor.ProfesorConsultaRepository;
import backend.people.application.port.profesor.ProfesorResumen;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarProfesoresUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    private final ProfesorConsultaRepository repo;
    private final AutorizacionService auth;

    public ListarProfesoresUseCase(ProfesorConsultaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    public List<ProfesorResumen> ejecutar(UUID usuarioId) {
        return repo.listarPorInstitucion(auth.institucionConRol(usuarioId, ROLES));
    }
}