package backend.security.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.security.domain.model.UsuarioInstitucion;
import backend.security.infrastructure.persistence.entity.UsuarioInstitucionEntity;

@Component
public class UsuarioInstitucionMapper {

    public UsuarioInstitucion toDomain(UsuarioInstitucionEntity entity) {
        if (entity == null) return null;
        UsuarioInstitucion domain = new UsuarioInstitucion();
        domain.setId(entity.getId());
        domain.setUsuarioId(entity.getUsuarioId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setEsPrincipal(entity.getEsPrincipal());
        domain.setActivo(entity.getActivo());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public UsuarioInstitucionEntity toEntity(UsuarioInstitucion domain) {
        if (domain == null) return null;
        UsuarioInstitucionEntity entity = new UsuarioInstitucionEntity();
        entity.setId(domain.getId());
        entity.setUsuarioId(domain.getUsuarioId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setEsPrincipal(domain.getEsPrincipal());
        entity.setActivo(domain.getActivo());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
