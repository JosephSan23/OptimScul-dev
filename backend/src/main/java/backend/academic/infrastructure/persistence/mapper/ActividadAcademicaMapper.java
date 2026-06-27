package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.infrastructure.persistence.entity.ActividadAcademicaEntity;

@Component
public class ActividadAcademicaMapper {

    public ActividadAcademica toDomain(ActividadAcademicaEntity entity) {
        if (entity == null) return null;
        ActividadAcademica domain = new ActividadAcademica();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCargaAcademicaId(entity.getCargaAcademicaId());
        domain.setPeriodoAcademicoId(entity.getPeriodoAcademicoId());
        domain.setSesionClaseId(entity.getSesionClaseId());
        domain.setTipo(entity.getTipo());
        domain.setTitulo(entity.getTitulo());
        domain.setDescripcion(entity.getDescripcion());
        domain.setFechaPublicacion(entity.getFechaPublicacion());
        domain.setFechaEntrega(entity.getFechaEntrega());
        domain.setFechaCierre(entity.getFechaCierre());
        domain.setPorcentaje(entity.getPorcentaje());
        domain.setNotaMaxima(entity.getNotaMaxima());
        domain.setPermiteEntregaTardia(entity.getPermiteEntregaTardia());
        domain.setEstado(entity.getEstado());
        domain.setCreadaPorUsuarioId(entity.getCreadaPorUsuarioId());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public ActividadAcademicaEntity toEntity(ActividadAcademica domain) {
        if (domain == null) return null;
        ActividadAcademicaEntity entity = new ActividadAcademicaEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCargaAcademicaId(domain.getCargaAcademicaId());
        entity.setPeriodoAcademicoId(domain.getPeriodoAcademicoId());
        entity.setSesionClaseId(domain.getSesionClaseId());
        entity.setTipo(domain.getTipo());
        entity.setTitulo(domain.getTitulo());
        entity.setDescripcion(domain.getDescripcion());
        entity.setFechaPublicacion(domain.getFechaPublicacion());
        entity.setFechaEntrega(domain.getFechaEntrega());
        entity.setFechaCierre(domain.getFechaCierre());
        entity.setPorcentaje(domain.getPorcentaje());
        entity.setNotaMaxima(domain.getNotaMaxima());
        entity.setPermiteEntregaTardia(domain.getPermiteEntregaTardia());
        entity.setEstado(domain.getEstado());
        entity.setCreadaPorUsuarioId(domain.getCreadaPorUsuarioId());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
