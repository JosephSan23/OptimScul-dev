package backend.coexistence.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.coexistence.domain.model.TipoObservacion;
import backend.coexistence.infrastructure.persistence.entity.TipoObservacionEntity;

@Component
public class TipoObservacionMapper {

    public TipoObservacion toDomain(TipoObservacionEntity entity) {
        if (entity == null) return null;
        TipoObservacion domain = new TipoObservacion();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setSeveridad(entity.getSeveridad());
        domain.setActiva(entity.getActiva());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public TipoObservacionEntity toEntity(TipoObservacion domain) {
        if (domain == null) return null;
        TipoObservacionEntity entity = new TipoObservacionEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setSeveridad(domain.getSeveridad());
        entity.setActiva(domain.getActiva());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
