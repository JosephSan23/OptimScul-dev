package backend.security.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.security.domain.model.RolPermiso;
import backend.security.infrastructure.persistence.entity.RolPermisoEntity;

@Component
public class RolPermisoMapper {

    public RolPermiso toDomain(RolPermisoEntity entity) {
        if (entity == null) return null;
        RolPermiso domain = new RolPermiso();
        domain.setId(entity.getId());
        domain.setRolId(entity.getRolId());
        domain.setPermisoId(entity.getPermisoId());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }

    public RolPermisoEntity toEntity(RolPermiso domain) {
        if (domain == null) return null;
        RolPermisoEntity entity = new RolPermisoEntity();
        entity.setId(domain.getId());
        entity.setRolId(domain.getRolId());
        entity.setPermisoId(domain.getPermisoId());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
