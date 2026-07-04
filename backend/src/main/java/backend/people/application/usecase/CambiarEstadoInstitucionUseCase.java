package backend.people.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.EstadoInstitucion;
import backend.people.domain.model.Institucion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoInstitucionUseCase {

    private final InstitucionRepository institucionRepository;

    public CambiarEstadoInstitucionUseCase(InstitucionRepository institucionRepository) {
        this.institucionRepository = institucionRepository;
    }

    public Institucion suspender(UUID id) {
        return cambiarEstado(id, EstadoInstitucion.SUSPENDIDA);
    }

    public Institucion reactivar(UUID id) {
        return cambiarEstado(id, EstadoInstitucion.ACTIVA);
    }

    public Institucion activar(UUID id) {
        return cambiarEstado(id, EstadoInstitucion.ACTIVA);
    }

    private Institucion cambiarEstado(UUID id, EstadoInstitucion nuevoEstado) {
        Institucion existente = institucionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe una institución con el id " + id));

        existente.setEstado(nuevoEstado);
        existente.setUpdatedAt(LocalDateTime.now());

        return institucionRepository.save(existente);
    }
}