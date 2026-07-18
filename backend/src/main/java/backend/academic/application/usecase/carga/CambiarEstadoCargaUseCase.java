package backend.academic.application.usecase.carga;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.domain.model.CargaAcademica;
import backend.academic.domain.model.EstadoCargaAcademica;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoCargaUseCase {
    private final CargaAcademicaRepository repo;
    private final AutorizacionService auth;

    public CambiarEstadoCargaUseCase(CargaAcademicaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    @Transactional public void finalizar(UUID u, UUID id)  { cambiar(u, id, EstadoCargaAcademica.FINALIZADA); }
    @Transactional public void cancelar(UUID u, UUID id)   { cambiar(u, id, EstadoCargaAcademica.CANCELADA); }
    @Transactional public void reactivar(UUID u, UUID id)  { cambiar(u, id, EstadoCargaAcademica.ACTIVA); }

    private void cambiar(UUID usuarioId, UUID cargaId, EstadoCargaAcademica estado) {
        UUID inst = auth.institucionConRol(usuarioId, ListarProfesoresUseCase.ROLES);
        CargaAcademica c = repo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La carga académica no existe."));
        if (!inst.equals(c.getInstitucionId()))
            throw new SecurityException("No puedes cambiar cargas de otra institución.");
        if (estado == EstadoCargaAcademica.ACTIVA
                && repo.existsActiva(inst, c.getAnioLectivoId(), c.getGrupoId(), c.getAsignaturaId()))
            throw new RuntimeException("Ya hay otra carga activa para ese grupo y asignatura.");
        c.setEstado(estado);
        c.setUpdatedAt(LocalDateTime.now());
        repo.save(c);
    }
}