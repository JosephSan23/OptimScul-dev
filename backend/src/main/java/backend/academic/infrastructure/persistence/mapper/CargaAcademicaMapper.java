package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.CargaAcademica;
import backend.academic.infrastructure.persistence.entity.CargaAcademicaEntity;

@Component
public class CargaAcademicaMapper {

    public CargaAcademica toDomain(CargaAcademicaEntity entity) {
        if (entity == null) return null;
        CargaAcademica domain = new CargaAcademica();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setProfesorId(entity.getProfesorId());
        domain.setGrupoId(entity.getGrupoId());
        domain.setAsignaturaId(entity.getAsignaturaId());
        domain.setIntensidadHorariaSemanal(entity.getIntensidadHorariaSemanal());
        domain.setFechaInicio(entity.getFechaInicio());
        domain.setFechaFin(entity.getFechaFin());
        domain.setEstado(entity.getEstado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public CargaAcademicaEntity toEntity(CargaAcademica domain) {
        if (domain == null) return null;
        CargaAcademicaEntity entity = new CargaAcademicaEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setProfesorId(domain.getProfesorId());
        entity.setGrupoId(domain.getGrupoId());
        entity.setAsignaturaId(domain.getAsignaturaId());
        entity.setIntensidadHorariaSemanal(domain.getIntensidadHorariaSemanal());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setFechaFin(domain.getFechaFin());
        entity.setEstado(domain.getEstado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
