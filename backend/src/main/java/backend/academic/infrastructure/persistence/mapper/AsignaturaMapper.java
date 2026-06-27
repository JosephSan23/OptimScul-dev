package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.Asignatura;
import backend.academic.infrastructure.persistence.entity.AsignaturaEntity;

@Component
public class AsignaturaMapper {

    public Asignatura toDomain(AsignaturaEntity entity) {
        if (entity == null) return null;
        Asignatura domain = new Asignatura();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setAreaId(entity.getAreaId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setIntensidadHorariaSemanal(entity.getIntensidadHorariaSemanal());
        domain.setRequiereCalificacion(entity.getRequiereCalificacion());
        domain.setRequiereRecuperacion(entity.getRequiereRecuperacion());
        domain.setEsComportamiento(entity.getEsComportamiento());
        domain.setActiva(entity.getActiva());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public AsignaturaEntity toEntity(Asignatura domain) {
        if (domain == null) return null;
        AsignaturaEntity entity = new AsignaturaEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setAreaId(domain.getAreaId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setIntensidadHorariaSemanal(domain.getIntensidadHorariaSemanal());
        entity.setRequiereCalificacion(domain.getRequiereCalificacion());
        entity.setRequiereRecuperacion(domain.getRequiereRecuperacion());
        entity.setEsComportamiento(domain.getEsComportamiento());
        entity.setActiva(domain.getActiva());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
