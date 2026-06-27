package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.CalificacionActividad;
import backend.academic.infrastructure.persistence.entity.CalificacionActividadEntity;

@Component
public class CalificacionActividadMapper {

    public CalificacionActividad toDomain(CalificacionActividadEntity entity) {
        if (entity == null) return null;
        CalificacionActividad domain = new CalificacionActividad();
        domain.setId(entity.getId());
        domain.setActividadId(entity.getActividadId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setEntregaActividadId(entity.getEntregaActividadId());
        domain.setNotaObtenida(entity.getNotaObtenida());
        domain.setObservacionDocente(entity.getObservacionDocente());
        domain.setCalificadaPorUsuarioId(entity.getCalificadaPorUsuarioId());
        domain.setFechaCalificacion(entity.getFechaCalificacion());
        domain.setEsRecuperacion(entity.getEsRecuperacion());
        domain.setAnulada(entity.getAnulada());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public CalificacionActividadEntity toEntity(CalificacionActividad domain) {
        if (domain == null) return null;
        CalificacionActividadEntity entity = new CalificacionActividadEntity();
        entity.setId(domain.getId());
        entity.setActividadId(domain.getActividadId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setEntregaActividadId(domain.getEntregaActividadId());
        entity.setNotaObtenida(domain.getNotaObtenida());
        entity.setObservacionDocente(domain.getObservacionDocente());
        entity.setCalificadaPorUsuarioId(domain.getCalificadaPorUsuarioId());
        entity.setFechaCalificacion(domain.getFechaCalificacion());
        entity.setEsRecuperacion(domain.getEsRecuperacion());
        entity.setAnulada(domain.getAnulada());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
