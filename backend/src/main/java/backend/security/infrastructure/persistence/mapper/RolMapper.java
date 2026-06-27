package backend.security.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.security.domain.model.Rol;
import backend.security.infrastructure.persistence.entity.RolEntity;

@Component
public class RolMapper {

    public Rol toDomain(RolEntity entity) {
        if (entity == null) return null;
        Rol domain = new Rol();
        domain.setId(entity.getId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setEsSistema(entity.getEsSistema());
        domain.setActivo(entity.getActivo());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public RolEntity toEntity(Rol domain) {
        if (domain == null) return null;
        RolEntity entity = new RolEntity();
        entity.setId(domain.getId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setEsSistema(domain.getEsSistema());
        entity.setActivo(domain.getActivo());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
