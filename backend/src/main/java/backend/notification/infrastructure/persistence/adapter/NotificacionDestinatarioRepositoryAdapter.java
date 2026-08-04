package backend.notification.infrastructure.persistence.adapter;

import backend.notification.application.NotificacionVista;
import backend.notification.application.port.NotificacionDestinatarioRepository;
import backend.notification.domain.model.EstadoNotificacion;
import backend.notification.domain.model.NotificacionDestinatario;
import backend.notification.infrastructure.persistence.NotificacionDestinatarioJpaRepository;
import backend.notification.infrastructure.persistence.mapper.NotificacionDestinatarioMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class NotificacionDestinatarioRepositoryAdapter implements NotificacionDestinatarioRepository {

    private final NotificacionDestinatarioJpaRepository jpa;
    private final NotificacionDestinatarioMapper mapper;

    public NotificacionDestinatarioRepositoryAdapter(NotificacionDestinatarioJpaRepository jpa,
            NotificacionDestinatarioMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public NotificacionDestinatario save(NotificacionDestinatario d) {
        return mapper.toDomain(jpa.save(mapper.toEntity(d)));
    }

    @Override
    public List<NotificacionVista> bandeja(UUID usuarioId, int limite) {
        return jpa.bandeja(usuarioId, limite).stream().map(r -> new NotificacionVista(
                r.getId(), r.getNotificacionId(), r.getTipo(), r.getTitulo(), r.getMensaje(),
                r.getModuloRelacionado(), r.getEntidadRelacionadaId(),
                r.getPrioridad() == null ? null : r.getPrioridad().intValue(),
                r.getEstado(), r.getLeidaEn(), r.getCreatedAt())).toList();
    }

    @Override
    public long contarNoLeidas(UUID usuarioId) {
        return jpa.contarNoLeidas(usuarioId);
    }

    @Override
    @Transactional
    public int marcarLeida(UUID id, UUID usuarioId) {
        return jpa.marcarLeida(id, usuarioId);
    }

    @Override
    @Transactional
    public int marcarTodas(UUID usuarioId) {
        return jpa.marcarTodas(usuarioId);
    }

    @Override
    @Transactional
    public int marcarLeidasDeConversacion(UUID usuarioId, UUID convId) {
        return jpa.marcarLeidasDeConversacion(usuarioId, convId);
    }

    @Override
    @Transactional
    public void actualizarEstado(UUID destinatarioId, EstadoNotificacion estado, String error) {
        jpa.findById(destinatarioId).ifPresent(e -> {
            e.setEstado(estado);
            e.setErrorEnvio(error);
            if (estado == EstadoNotificacion.ENVIADA)
                e.setEnviadaEn(LocalDateTime.now());
            e.setUpdatedAt(LocalDateTime.now());
            jpa.save(e);
        });
    }
}