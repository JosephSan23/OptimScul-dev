package backend.shared.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.shared.domain.model.AuditoriaEvento;
import backend.shared.infrastructure.persistence.entity.AuditoriaEventoEntity;

@Component
public class AuditoriaEventoMapper {

    public AuditoriaEvento toDomain(AuditoriaEventoEntity entity) {
        if (entity == null) return null;
        AuditoriaEvento domain = new AuditoriaEvento();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setUsuarioId(entity.getUsuarioId());
        domain.setModulo(entity.getModulo());
        domain.setEntidad(entity.getEntidad());
        domain.setEntidadId(entity.getEntidadId());
        domain.setAccion(entity.getAccion());
        domain.setDescripcion(entity.getDescripcion());
        domain.setValoresAntes(entity.getValoresAntes());
        domain.setValoresDespues(entity.getValoresDespues());
        domain.setIp(entity.getIp());
        domain.setUserAgent(entity.getUserAgent());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }

    public AuditoriaEventoEntity toEntity(AuditoriaEvento domain) {
        if (domain == null) return null;
        AuditoriaEventoEntity entity = new AuditoriaEventoEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setUsuarioId(domain.getUsuarioId());
        entity.setModulo(domain.getModulo());
        entity.setEntidad(domain.getEntidad());
        entity.setEntidadId(domain.getEntidadId());
        entity.setAccion(domain.getAccion());
        entity.setDescripcion(domain.getDescripcion());
        entity.setValoresAntes(domain.getValoresAntes());
        entity.setValoresDespues(domain.getValoresDespues());
        entity.setIp(domain.getIp());
        entity.setUserAgent(domain.getUserAgent());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
