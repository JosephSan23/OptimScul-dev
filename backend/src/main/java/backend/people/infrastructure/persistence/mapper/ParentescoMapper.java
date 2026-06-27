package backend.people.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.people.domain.model.Parentesco;
import backend.people.infrastructure.persistence.entity.ParentescoEntity;

@Component
public class ParentescoMapper {

    public Parentesco toDomain(ParentescoEntity entity) {
        if (entity == null) return null;
        Parentesco domain = new Parentesco();
        domain.setId(entity.getId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setActivo(entity.getActivo());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public ParentescoEntity toEntity(Parentesco domain) {
        if (domain == null) return null;
        ParentescoEntity entity = new ParentescoEntity();
        entity.setId(domain.getId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setActivo(domain.getActivo());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
