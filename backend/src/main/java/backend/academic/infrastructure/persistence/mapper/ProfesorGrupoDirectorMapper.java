package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.ProfesorGrupoDirector;
import backend.academic.infrastructure.persistence.entity.ProfesorGrupoDirectorEntity;

@Component
public class ProfesorGrupoDirectorMapper {

    public ProfesorGrupoDirector toDomain(ProfesorGrupoDirectorEntity entity) {
        if (entity == null) return null;
        ProfesorGrupoDirector domain = new ProfesorGrupoDirector();
        domain.setId(entity.getId());
        domain.setGrupoId(entity.getGrupoId());
        domain.setProfesorId(entity.getProfesorId());
        domain.setFechaInicio(entity.getFechaInicio());
        domain.setFechaFin(entity.getFechaFin());
        domain.setActivo(entity.getActivo());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }

    public ProfesorGrupoDirectorEntity toEntity(ProfesorGrupoDirector domain) {
        if (domain == null) return null;
        ProfesorGrupoDirectorEntity entity = new ProfesorGrupoDirectorEntity();
        entity.setId(domain.getId());
        entity.setGrupoId(domain.getGrupoId());
        entity.setProfesorId(domain.getProfesorId());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setFechaFin(domain.getFechaFin());
        entity.setActivo(domain.getActivo());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
