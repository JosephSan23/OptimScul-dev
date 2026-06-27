package backend.notification.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.notification.domain.model.NotificacionDestinatario;
import backend.notification.infrastructure.persistence.entity.NotificacionDestinatarioEntity;

@Component
public class NotificacionDestinatarioMapper {

    public NotificacionDestinatario toDomain(NotificacionDestinatarioEntity entity) {
        if (entity == null) return null;
        NotificacionDestinatario domain = new NotificacionDestinatario();
        domain.setId(entity.getId());
        domain.setNotificacionId(entity.getNotificacionId());
        domain.setUsuarioId(entity.getUsuarioId());
        domain.setCanal(entity.getCanal());
        domain.setEstado(entity.getEstado());
        domain.setEnviadaEn(entity.getEnviadaEn());
        domain.setLeidaEn(entity.getLeidaEn());
        domain.setErrorEnvio(entity.getErrorEnvio());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public NotificacionDestinatarioEntity toEntity(NotificacionDestinatario domain) {
        if (domain == null) return null;
        NotificacionDestinatarioEntity entity = new NotificacionDestinatarioEntity();
        entity.setId(domain.getId());
        entity.setNotificacionId(domain.getNotificacionId());
        entity.setUsuarioId(domain.getUsuarioId());
        entity.setCanal(domain.getCanal());
        entity.setEstado(domain.getEstado());
        entity.setEnviadaEn(domain.getEnviadaEn());
        entity.setLeidaEn(domain.getLeidaEn());
        entity.setErrorEnvio(domain.getErrorEnvio());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
