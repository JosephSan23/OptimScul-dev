package backend.enrollment.application.usecase;

import backend.enrollment.application.port.MatriculaRepository;
import backend.enrollment.domain.model.EstadoMatricula;
import backend.enrollment.domain.model.Matricula;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoMatriculaUseCase {
    private final MatriculaRepository repo;
    private final CrearMatriculaUseCase crearUseCase; // reutiliza validarGrupo (cupo)
    private final AutorizacionService auth;

    public CambiarEstadoMatriculaUseCase(MatriculaRepository repo, CrearMatriculaUseCase crearUseCase,
            AutorizacionService auth) {
        this.repo = repo;
        this.crearUseCase = crearUseCase;
        this.auth = auth;
    }

    @Transactional
    public void retirar(UUID u, UUID id) {
        cambiar(u, id, EstadoMatricula.RETIRADO);
    }

    @Transactional
    public void cancelar(UUID u, UUID id) {
        cambiar(u, id, EstadoMatricula.CANCELADO);
    }

    @Transactional
    public void finalizar(UUID u, UUID id) {
        cambiar(u, id, EstadoMatricula.FINALIZADO);
    }

    @Transactional
    public void reactivar(UUID usuarioId, UUID matriculaId) {
        UUID inst = auth.institucionConRol(usuarioId, CrearMatriculaUseCase.ROLES);
        Matricula m = obtener(inst, matriculaId);

        // Vuelve a MATRICULADO si tenía grupo (revalidando cupo), o a PREMATRICULA si
        // no
        if (m.getGrupoId() != null) {
            crearUseCase.validarGrupo(inst, m.getAnioLectivoId(), m.getGrupoId());
            m.setEstado(EstadoMatricula.MATRICULADO);
        } else {
            m.setEstado(EstadoMatricula.PREMATRICULA);
        }
        m.setFechaEstado(LocalDate.now());
        m.setUpdatedBy(usuarioId);
        m.setUpdatedAt(LocalDateTime.now());
        repo.save(m);
    }

    private void cambiar(UUID usuarioId, UUID matriculaId, EstadoMatricula estado) {
        UUID inst = auth.institucionConRol(usuarioId, CrearMatriculaUseCase.ROLES);
        Matricula m = obtener(inst, matriculaId);
        m.setEstado(estado);
        m.setFechaEstado(LocalDate.now());
        m.setUpdatedBy(usuarioId);
        m.setUpdatedAt(LocalDateTime.now());
        repo.save(m);
    }

    private Matricula obtener(UUID inst, UUID matriculaId) {
        Matricula m = repo.findById(matriculaId)
                .orElseThrow(() -> new RuntimeException("La matrícula no existe."));
        if (!inst.equals(m.getInstitucionId()))
            throw new SecurityException("No puedes modificar matrículas de otra institución.");
        return m;
    }
}