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
public class AsignarGrupoUseCase {
    private final MatriculaRepository matriculaRepo;
    private final CrearMatriculaUseCase crearUseCase;   // reutiliza validarGrupo (con cupo)
    private final AutorizacionService auth;

    public AsignarGrupoUseCase(MatriculaRepository matriculaRepo, CrearMatriculaUseCase crearUseCase,
                               AutorizacionService auth) {
        this.matriculaRepo = matriculaRepo; this.crearUseCase = crearUseCase; this.auth = auth;
    }

    @Transactional
    public Matricula ejecutar(UUID usuarioId, UUID matriculaId, UUID grupoId) {
        UUID inst = auth.institucionConRol(usuarioId, CrearMatriculaUseCase.ROLES);
        Matricula m = matriculaRepo.findById(matriculaId)
                .orElseThrow(() -> new RuntimeException("La matrícula no existe."));
        if (!inst.equals(m.getInstitucionId()))
            throw new SecurityException("No puedes modificar matrículas de otra institución.");
        if (m.getEstado() != EstadoMatricula.PREMATRICULA && m.getEstado() != EstadoMatricula.MATRICULADO)
            throw new RuntimeException("Solo se puede asignar grupo a matrículas vigentes.");

        crearUseCase.validarGrupo(inst, m.getAnioLectivoId(), grupoId);

        m.setGrupoId(grupoId);
        if (m.getEstado() == EstadoMatricula.PREMATRICULA) {
            m.setEstado(EstadoMatricula.MATRICULADO);
            m.setFechaEstado(LocalDate.now());
        }
        m.setUpdatedBy(usuarioId);
        m.setUpdatedAt(LocalDateTime.now());
        return matriculaRepo.save(m);
    }
}