package backend.people.application;

import backend.people.application.port.ProfesorRepository;
import backend.people.domain.model.EstadoProfesor;
import backend.people.domain.model.Profesor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PerfilProfesorService {

    private final ProfesorRepository profesorRepository;

    public PerfilProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    /**
     * Garantiza que la persona tenga perfil de profesor ACTIVO.
     * Si no existe lo crea; si existía retirado/inactivo, lo reactiva.
     * Idempotente: llamable desde crear staff, editar staff o reactivar.
     */
    public Profesor asegurarPerfil(UUID institucionId, UUID personaId, String numeroDocumento) {
        return profesorRepository.findByInstitucionIdAndPersonaId(institucionId, personaId)
                .map(this::reactivar)
                .orElseGet(() -> crear(institucionId, personaId, numeroDocumento));
    }

    /** Reactiva el perfil solo si ya existía. No crea nada (para no volver profesor a quien no lo es). */
    public void reactivarPerfilSiExiste(UUID institucionId, UUID personaId) {
        profesorRepository.findByInstitucionIdAndPersonaId(institucionId, personaId)
                .ifPresent(this::reactivar);
    }

    /** Marca el perfil como retirado (si existe). Solo toca la tabla profesor — nunca los roles. */
    public void inactivarPerfil(UUID institucionId, UUID personaId) {
        profesorRepository.findByInstitucionIdAndPersonaId(institucionId, personaId).ifPresent(p -> {
            p.setEstado(EstadoProfesor.RETIRADO);
            p.setFechaRetiro(LocalDate.now());
            p.setUpdatedAt(LocalDateTime.now());
            profesorRepository.save(p);
        });
    }

    private Profesor reactivar(Profesor p) {
        if (p.getEstado() != EstadoProfesor.ACTIVO) {
            p.setEstado(EstadoProfesor.ACTIVO);
            p.setFechaRetiro(null);
            p.setUpdatedAt(LocalDateTime.now());
            return profesorRepository.save(p);
        }
        return p;
    }

    private Profesor crear(UUID institucionId, UUID personaId, String numeroDocumento) {
        LocalDateTime ahora = LocalDateTime.now();
        Profesor p = new Profesor();
        p.setId(UUID.randomUUID());
        p.setInstitucionId(institucionId);
        p.setPersonaId(personaId);
        p.setCodigoProfesor("DOC-" + numeroDocumento);
        p.setFechaVinculacion(LocalDate.now());
        p.setEstado(EstadoProfesor.ACTIVO);
        p.setCreatedAt(ahora);
        p.setUpdatedAt(ahora);
        return profesorRepository.save(p);
    }
}