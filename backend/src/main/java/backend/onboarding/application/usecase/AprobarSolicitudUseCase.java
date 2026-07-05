package backend.onboarding.application.usecase;

import backend.onboarding.application.port.SolicitudInstitucionRepository;
import backend.onboarding.domain.model.SolicitudInstitucion;
import backend.people.application.usecase.CrearInstitucionUseCase;
import backend.people.domain.model.Institucion;
import backend.people.domain.model.TipoInstitucion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AprobarSolicitudUseCase {

    private final SolicitudInstitucionRepository solicitudRepository;
    private final CrearInstitucionUseCase crearInstitucionUseCase;

    public AprobarSolicitudUseCase(SolicitudInstitucionRepository solicitudRepository,
                                   CrearInstitucionUseCase crearInstitucionUseCase) {
        this.solicitudRepository = solicitudRepository;
        this.crearInstitucionUseCase = crearInstitucionUseCase;
    }

    @Transactional
    public SolicitudInstitucion ejecutar(UUID solicitudId, UUID aprobadaPorUsuarioId) {

        // 1. Buscar la solicitud (o error si no existe)
        SolicitudInstitucion solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("No existe una solicitud con el id " + solicitudId));

        // 2. Solo se puede aprobar una PENDIENTE
        if (!"PENDIENTE".equals(solicitud.getEstado())) {
            throw new RuntimeException("Esta solicitud ya fue procesada (estado: " + solicitud.getEstado() + ").");
        }

        // 3. Construir la institución a partir de los datos de la solicitud
        Institucion nueva = new Institucion();
        nueva.setCodigo(generarCodigo());
        nueva.setNombre(solicitud.getNombreColegio());
        nueva.setTipoInstitucion(TipoInstitucion.COLEGIO);   // por defecto; se puede editar luego
        nueva.setNit(solicitud.getNit());
        nueva.setCiudad(solicitud.getCiudad());
        nueva.setDireccionPrincipal(solicitud.getDireccion());
        nueva.setTelefonoContacto(solicitud.getTelefono());
        nueva.setCorreoContacto(solicitud.getCorreo());

        // 4. Crear la institución reutilizando el caso de uso de people
        Institucion institucionCreada = crearInstitucionUseCase.ejecutar(nueva);

        // 5. Marcar la solicitud como aprobada y enlazarla con la institución
        solicitud.setEstado("APROBADA");
        solicitud.setRevisadaPorUsuarioId(aprobadaPorUsuarioId);
        solicitud.setFechaRevision(LocalDateTime.now());
        solicitud.setConvertidaEnInstitucionId(institucionCreada.getId());
        solicitud.setFechaConversion(LocalDateTime.now());
        solicitud.setUpdatedAt(LocalDateTime.now());

        return solicitudRepository.save(solicitud);
    }

    private String generarCodigo() {
        // Código temporal único; el super admin puede ajustarlo luego en el CRUD
        return "SOL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}