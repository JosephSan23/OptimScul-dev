package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.AreaAcademica;
import backend.academic.infrastructure.persistence.entity.AreaAcademicaEntity;

@Component
public class AreaAcademicaMapper {

    public AreaAcademica toDomain(AreaAcademicaEntity entity) {
        if (entity == null) return null;
        AreaAcademica domain = new AreaAcademica();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setActiva(entity.getActiva());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public AreaAcademicaEntity toEntity(AreaAcademica domain) {
        if (domain == null) return null;
        AreaAcademicaEntity entity = new AreaAcademicaEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setActiva(domain.getActiva());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
