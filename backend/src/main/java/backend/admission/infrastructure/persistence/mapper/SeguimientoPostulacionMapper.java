package backend.admission.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.admission.domain.model.SeguimientoPostulacion;
import backend.admission.infrastructure.persistence.entity.SeguimientoPostulacionEntity;

@Component
public class SeguimientoPostulacionMapper {

    public SeguimientoPostulacion toDomain(SeguimientoPostulacionEntity entity) {
        if (entity == null) return null;
        SeguimientoPostulacion domain = new SeguimientoPostulacion();
        domain.setId(entity.getId());
        domain.setPostulacionId(entity.getPostulacionId());
        domain.setComentario(entity.getComentario());
        domain.setEstadoAnterior(entity.getEstadoAnterior());
        domain.setEstadoNuevo(entity.getEstadoNuevo());
        domain.setCreadoPorUsuarioId(entity.getCreadoPorUsuarioId());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }

    public SeguimientoPostulacionEntity toEntity(SeguimientoPostulacion domain) {
        if (domain == null) return null;
        SeguimientoPostulacionEntity entity = new SeguimientoPostulacionEntity();
        entity.setId(domain.getId());
        entity.setPostulacionId(domain.getPostulacionId());
        entity.setComentario(domain.getComentario());
        entity.setEstadoAnterior(domain.getEstadoAnterior());
        entity.setEstadoNuevo(domain.getEstadoNuevo());
        entity.setCreadoPorUsuarioId(domain.getCreadoPorUsuarioId());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
