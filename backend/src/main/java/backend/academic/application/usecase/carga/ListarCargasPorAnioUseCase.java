package backend.academic.application.usecase.carga;

import backend.academic.application.port.CargaAcademica.CargaConsultaRepository;
import backend.academic.application.port.CargaAcademica.CargaResumen;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarCargasPorAnioUseCase {
    private final CargaConsultaRepository repo;
    private final AutorizacionService auth;

    public ListarCargasPorAnioUseCase(CargaConsultaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    public List<CargaResumen> ejecutar(UUID usuarioId, UUID anioId) {
        return repo.listarPorAnio(auth.institucionConRol(usuarioId, ListarProfesoresUseCase.ROLES), anioId);
    }
}