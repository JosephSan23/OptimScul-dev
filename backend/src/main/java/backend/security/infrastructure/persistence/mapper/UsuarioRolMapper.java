package backend.security.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.security.domain.model.UsuarioRol;
import backend.security.infrastructure.persistence.entity.UsuarioRolEntity;

@Component
public class UsuarioRolMapper {

    public UsuarioRol toDomain(UsuarioRolEntity entity) {
        if (entity == null) return null;
        UsuarioRol domain = new UsuarioRol();
        domain.setId(entity.getId());
        domain.setUsuarioId(entity.getUsuarioId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setRolId(entity.getRolId());
        domain.setActivo(entity.getActivo());
        domain.setFechaInicio(entity.getFechaInicio());
        domain.setFechaFin(entity.getFechaFin());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public UsuarioRolEntity toEntity(UsuarioRol domain) {
        if (domain == null) return null;
        UsuarioRolEntity entity = new UsuarioRolEntity();
        entity.setId(domain.getId());
        entity.setUsuarioId(domain.getUsuarioId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setRolId(domain.getRolId());
        entity.setActivo(domain.getActivo());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setFechaFin(domain.getFechaFin());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
