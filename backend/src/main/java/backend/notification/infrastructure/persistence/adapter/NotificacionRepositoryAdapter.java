package backend.notification.infrastructure.persistence.adapter;

import backend.notification.application.port.NotificacionRepository;
import backend.notification.domain.model.Notificacion;
import backend.notification.infrastructure.persistence.NotificacionJpaRepository;
import backend.notification.infrastructure.persistence.mapper.NotificacionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotificacionRepositoryAdapter implements NotificacionRepository {

    private final NotificacionJpaRepository jpa;
    private final NotificacionMapper mapper;

    public NotificacionRepositoryAdapter(NotificacionJpaRepository jpa, NotificacionMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Notificacion save(Notificacion n) {
        return mapper.toDomain(jpa.save(mapper.toEntity(n)));
    }

    @Override
    public Optional<Notificacion> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Notificacion> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }
}