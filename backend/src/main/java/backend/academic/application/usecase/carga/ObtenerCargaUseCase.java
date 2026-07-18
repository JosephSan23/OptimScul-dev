package backend.academic.application.usecase.carga;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.domain.model.CargaAcademica;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerCargaUseCase {
    private final CargaAcademicaRepository repo;
    private final AutorizacionService auth;

    public ObtenerCargaUseCase(CargaAcademicaRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    public CargaAcademica ejecutar(UUID usuarioId, UUID cargaId) {
        UUID inst = auth.institucionConRol(usuarioId, ListarProfesoresUseCase.ROLES);
        CargaAcademica c = repo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La carga académica no existe."));
        if (!inst.equals(c.getInstitucionId()))
            throw new SecurityException("No puedes ver cargas de otra institución.");
        return c;
    }
}