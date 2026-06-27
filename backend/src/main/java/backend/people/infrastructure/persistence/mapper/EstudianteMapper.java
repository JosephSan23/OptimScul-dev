package backend.people.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.people.domain.model.Estudiante;
import backend.people.infrastructure.persistence.entity.EstudianteEntity;

@Component
public class EstudianteMapper {

    public Estudiante toDomain(EstudianteEntity entity) {
        if (entity == null) return null;
        Estudiante domain = new Estudiante();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setPersonaId(entity.getPersonaId());
        domain.setCodigoEstudiante(entity.getCodigoEstudiante());
        domain.setFechaIngreso(entity.getFechaIngreso());
        domain.setFechaRetiro(entity.getFechaRetiro());
        domain.setEstado(entity.getEstado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public EstudianteEntity toEntity(Estudiante domain) {
        if (domain == null) return null;
        EstudianteEntity entity = new EstudianteEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setPersonaId(domain.getPersonaId());
        entity.setCodigoEstudiante(domain.getCodigoEstudiante());
        entity.setFechaIngreso(domain.getFechaIngreso());
        entity.setFechaRetiro(domain.getFechaRetiro());
        entity.setEstado(domain.getEstado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
