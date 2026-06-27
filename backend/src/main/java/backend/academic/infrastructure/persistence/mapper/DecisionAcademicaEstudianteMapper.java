package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.DecisionAcademicaEstudiante;
import backend.academic.infrastructure.persistence.entity.DecisionAcademicaEstudianteEntity;

@Component
public class DecisionAcademicaEstudianteMapper {

    public DecisionAcademicaEstudiante toDomain(DecisionAcademicaEstudianteEntity entity) {
        if (entity == null) return null;
        DecisionAcademicaEstudiante domain = new DecisionAcademicaEstudiante();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setResultado(entity.getResultado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setFechaDecision(entity.getFechaDecision());
        domain.setUsuarioId(entity.getUsuarioId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public DecisionAcademicaEstudianteEntity toEntity(DecisionAcademicaEstudiante domain) {
        if (domain == null) return null;
        DecisionAcademicaEstudianteEntity entity = new DecisionAcademicaEstudianteEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setResultado(domain.getResultado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setFechaDecision(domain.getFechaDecision());
        entity.setUsuarioId(domain.getUsuarioId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
