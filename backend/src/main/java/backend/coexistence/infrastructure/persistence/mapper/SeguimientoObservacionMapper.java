package backend.coexistence.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.coexistence.domain.model.SeguimientoObservacion;
import backend.coexistence.infrastructure.persistence.entity.SeguimientoObservacionEntity;

@Component
public class SeguimientoObservacionMapper {

    public SeguimientoObservacion toDomain(SeguimientoObservacionEntity entity) {
        if (entity == null) return null;
        SeguimientoObservacion domain = new SeguimientoObservacion();
        domain.setId(entity.getId());
        domain.setObservacionId(entity.getObservacionId());
        domain.setComentario(entity.getComentario());
        domain.setEstadoAnterior(entity.getEstadoAnterior());
        domain.setEstadoNuevo(entity.getEstadoNuevo());
        domain.setCreadoPorUsuarioId(entity.getCreadoPorUsuarioId());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }

    public SeguimientoObservacionEntity toEntity(SeguimientoObservacion domain) {
        if (domain == null) return null;
        SeguimientoObservacionEntity entity = new SeguimientoObservacionEntity();
        entity.setId(domain.getId());
        entity.setObservacionId(domain.getObservacionId());
        entity.setComentario(domain.getComentario());
        entity.setEstadoAnterior(domain.getEstadoAnterior());
        entity.setEstadoNuevo(domain.getEstadoNuevo());
        entity.setCreadoPorUsuarioId(domain.getCreadoPorUsuarioId());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
