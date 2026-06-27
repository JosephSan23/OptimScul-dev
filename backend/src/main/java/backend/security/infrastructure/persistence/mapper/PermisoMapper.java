package backend.security.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.security.domain.model.Permiso;
import backend.security.infrastructure.persistence.entity.PermisoEntity;

@Component
public class PermisoMapper {

    public Permiso toDomain(PermisoEntity entity) {
        if (entity == null) return null;
        Permiso domain = new Permiso();
        domain.setId(entity.getId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setModulo(entity.getModulo());
        domain.setActivo(entity.getActivo());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public PermisoEntity toEntity(Permiso domain) {
        if (domain == null) return null;
        PermisoEntity entity = new PermisoEntity();
        entity.setId(domain.getId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setModulo(domain.getModulo());
        entity.setActivo(domain.getActivo());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
