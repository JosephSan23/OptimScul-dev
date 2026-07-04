package backend.people.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.EstadoInstitucion;
import backend.people.domain.model.Institucion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearInstitucionUseCase {

    private final InstitucionRepository institucionRepository;

    public CrearInstitucionUseCase(InstitucionRepository institucionRepository) {
        this.institucionRepository = institucionRepository;
    }

    public Institucion ejecutar(Institucion institucion) {

        // Regla de negocio: el código no se puede repetir
        if (institucionRepository.existsByCodigo(institucion.getCodigo())) {
            throw new RuntimeException("Ya existe una institución con el código " + institucion.getCodigo());
        }

        // El sistema asigna estos valores, no el cliente
        institucion.setId(UUID.randomUUID());
        institucion.setEstado(EstadoInstitucion.PRUEBA);

        // Valores por defecto si no vinieron
        if (esVacio(institucion.getPais()))        institucion.setPais("Colombia");
        if (esVacio(institucion.getZonaHoraria())) institucion.setZonaHoraria("America/Bogota");
        if (esVacio(institucion.getMoneda()))      institucion.setMoneda("COP");

        LocalDateTime ahora = LocalDateTime.now();
        institucion.setCreatedAt(ahora);
        institucion.setUpdatedAt(ahora);

        return institucionRepository.save(institucion);
    }

    private boolean esVacio(String s) {
        return s == null || s.isBlank();
    }
}