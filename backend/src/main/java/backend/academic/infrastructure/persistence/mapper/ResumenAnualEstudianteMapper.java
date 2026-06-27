package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.ResumenAnualEstudiante;
import backend.academic.infrastructure.persistence.entity.ResumenAnualEstudianteEntity;

@Component
public class ResumenAnualEstudianteMapper {

    public ResumenAnualEstudiante toDomain(ResumenAnualEstudianteEntity entity) {
        if (entity == null) return null;
        ResumenAnualEstudiante domain = new ResumenAnualEstudiante();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setAsignaturaId(entity.getAsignaturaId());
        domain.setNotaFinal(entity.getNotaFinal());
        domain.setAprueba(entity.getAprueba());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public ResumenAnualEstudianteEntity toEntity(ResumenAnualEstudiante domain) {
        if (domain == null) return null;
        ResumenAnualEstudianteEntity entity = new ResumenAnualEstudianteEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setAsignaturaId(domain.getAsignaturaId());
        entity.setNotaFinal(domain.getNotaFinal());
        entity.setAprueba(domain.getAprueba());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
