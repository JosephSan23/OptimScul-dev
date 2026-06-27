package backend.security.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.security.domain.model.Usuario;
import backend.security.infrastructure.persistence.entity.UsuarioEntity;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        Usuario domain = new Usuario();
        domain.setId(entity.getId());
        domain.setPersonaId(entity.getPersonaId());
        domain.setUsername(entity.getUsername());
        domain.setPasswordHash(entity.getPasswordHash());
        domain.setEmailLogin(entity.getEmailLogin());
        domain.setTipoContexto(entity.getTipoContexto());
        domain.setEstado(entity.getEstado());
        domain.setRequiereCambioPassword(entity.getRequiereCambioPassword());
        domain.setEmailVerificado(entity.getEmailVerificado());
        domain.setDobleFactorHabilitado(entity.getDobleFactorHabilitado());
        domain.setIntentosFallidos(entity.getIntentosFallidos());
        domain.setBloqueadoHasta(entity.getBloqueadoHasta());
        domain.setUltimoLogin(entity.getUltimoLogin());
        domain.setUltimoCambioPassword(entity.getUltimoCambioPassword());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) return null;
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(domain.getId());
        entity.setPersonaId(domain.getPersonaId());
        entity.setUsername(domain.getUsername());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setEmailLogin(domain.getEmailLogin());
        entity.setTipoContexto(domain.getTipoContexto());
        entity.setEstado(domain.getEstado());
        entity.setRequiereCambioPassword(domain.getRequiereCambioPassword());
        entity.setEmailVerificado(domain.getEmailVerificado());
        entity.setDobleFactorHabilitado(domain.getDobleFactorHabilitado());
        entity.setIntentosFallidos(domain.getIntentosFallidos());
        entity.setBloqueadoHasta(domain.getBloqueadoHasta());
        entity.setUltimoLogin(domain.getUltimoLogin());
        entity.setUltimoCambioPassword(domain.getUltimoCambioPassword());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
