package backend.people.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.people.domain.model.Jornada;
import backend.people.infrastructure.persistence.entity.JornadaEntity;

@Component
public class JornadaMapper {

    public Jornada toDomain(JornadaEntity entity) {
        if (entity == null) return null;
        Jornada domain = new Jornada();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setHoraInicio(entity.getHoraInicio());
        domain.setHoraFin(entity.getHoraFin());
        domain.setEstado(entity.getEstado());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public JornadaEntity toEntity(Jornada domain) {
        if (domain == null) return null;
        JornadaEntity entity = new JornadaEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setHoraInicio(domain.getHoraInicio());
        entity.setHoraFin(domain.getHoraFin());
        entity.setEstado(domain.getEstado());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
