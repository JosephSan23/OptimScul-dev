package backend.people.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.people.domain.model.Profesor;
import backend.people.infrastructure.persistence.entity.ProfesorEntity;

@Component
public class ProfesorMapper {

    public Profesor toDomain(ProfesorEntity entity) {
        if (entity == null) return null;
        Profesor domain = new Profesor();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setPersonaId(entity.getPersonaId());
        domain.setCodigoProfesor(entity.getCodigoProfesor());
        domain.setEspecialidad(entity.getEspecialidad());
        domain.setTituloProfesional(entity.getTituloProfesional());
        domain.setFechaVinculacion(entity.getFechaVinculacion());
        domain.setFechaRetiro(entity.getFechaRetiro());
        domain.setEstado(entity.getEstado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public ProfesorEntity toEntity(Profesor domain) {
        if (domain == null) return null;
        ProfesorEntity entity = new ProfesorEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setPersonaId(domain.getPersonaId());
        entity.setCodigoProfesor(domain.getCodigoProfesor());
        entity.setEspecialidad(domain.getEspecialidad());
        entity.setTituloProfesional(domain.getTituloProfesional());
        entity.setFechaVinculacion(domain.getFechaVinculacion());
        entity.setFechaRetiro(domain.getFechaRetiro());
        entity.setEstado(domain.getEstado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
