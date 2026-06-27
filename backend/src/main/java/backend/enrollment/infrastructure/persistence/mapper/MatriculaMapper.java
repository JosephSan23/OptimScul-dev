package backend.enrollment.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.enrollment.domain.model.Matricula;
import backend.enrollment.infrastructure.persistence.entity.MatriculaEntity;

@Component
public class MatriculaMapper {

    public Matricula toDomain(MatriculaEntity entity) {
        if (entity == null) return null;
        Matricula domain = new Matricula();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setGrupoId(entity.getGrupoId());
        domain.setCodigoMatricula(entity.getCodigoMatricula());
        domain.setTipo(entity.getTipo());
        domain.setEstado(entity.getEstado());
        domain.setFechaMatricula(entity.getFechaMatricula());
        domain.setFechaEstado(entity.getFechaEstado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public MatriculaEntity toEntity(Matricula domain) {
        if (domain == null) return null;
        MatriculaEntity entity = new MatriculaEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setGrupoId(domain.getGrupoId());
        entity.setCodigoMatricula(domain.getCodigoMatricula());
        entity.setTipo(domain.getTipo());
        entity.setEstado(domain.getEstado());
        entity.setFechaMatricula(domain.getFechaMatricula());
        entity.setFechaEstado(domain.getFechaEstado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
