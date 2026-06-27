package backend.notification.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.notification.domain.model.Notificacion;
import backend.notification.infrastructure.persistence.entity.NotificacionEntity;

@Component
public class NotificacionMapper {

    public Notificacion toDomain(NotificacionEntity entity) {
        if (entity == null) return null;
        Notificacion domain = new Notificacion();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setTipo(entity.getTipo());
        domain.setTitulo(entity.getTitulo());
        domain.setMensaje(entity.getMensaje());
        domain.setModuloRelacionado(entity.getModuloRelacionado());
        domain.setEntidadRelacionadaId(entity.getEntidadRelacionadaId());
        domain.setPrioridad(entity.getPrioridad());
        domain.setProgramadaPara(entity.getProgramadaPara());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }

    public NotificacionEntity toEntity(Notificacion domain) {
        if (domain == null) return null;
        NotificacionEntity entity = new NotificacionEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setTipo(domain.getTipo());
        entity.setTitulo(domain.getTitulo());
        entity.setMensaje(domain.getMensaje());
        entity.setModuloRelacionado(domain.getModuloRelacionado());
        entity.setEntidadRelacionadaId(domain.getEntidadRelacionadaId());
        entity.setPrioridad(domain.getPrioridad());
        entity.setProgramadaPara(domain.getProgramadaPara());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
