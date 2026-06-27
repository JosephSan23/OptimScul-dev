package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.EntregaActividad;
import backend.academic.infrastructure.persistence.entity.EntregaActividadEntity;

@Component
public class EntregaActividadMapper {

    public EntregaActividad toDomain(EntregaActividadEntity entity) {
        if (entity == null) return null;
        EntregaActividad domain = new EntregaActividad();
        domain.setId(entity.getId());
        domain.setActividadId(entity.getActividadId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setFechaEntrega(entity.getFechaEntrega());
        domain.setComentarioEstudiante(entity.getComentarioEstudiante());
        domain.setArchivoUrl(entity.getArchivoUrl());
        domain.setEstado(entity.getEstado());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public EntregaActividadEntity toEntity(EntregaActividad domain) {
        if (domain == null) return null;
        EntregaActividadEntity entity = new EntregaActividadEntity();
        entity.setId(domain.getId());
        entity.setActividadId(domain.getActividadId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setFechaEntrega(domain.getFechaEntrega());
        entity.setComentarioEstudiante(domain.getComentarioEstudiante());
        entity.setArchivoUrl(domain.getArchivoUrl());
        entity.setEstado(domain.getEstado());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
