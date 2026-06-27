package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.AsistenciaClase;
import backend.academic.infrastructure.persistence.entity.AsistenciaClaseEntity;

@Component
public class AsistenciaClaseMapper {

    public AsistenciaClase toDomain(AsistenciaClaseEntity entity) {
        if (entity == null) return null;
        AsistenciaClase domain = new AsistenciaClase();
        domain.setId(entity.getId());
        domain.setSesionClaseId(entity.getSesionClaseId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setTipoAsistencia(entity.getTipoAsistencia());
        domain.setObservacion(entity.getObservacion());
        domain.setJustificacion(entity.getJustificacion());
        domain.setMinutosTarde(entity.getMinutosTarde());
        domain.setRegistradaPorUsuarioId(entity.getRegistradaPorUsuarioId());
        domain.setFechaRegistro(entity.getFechaRegistro());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public AsistenciaClaseEntity toEntity(AsistenciaClase domain) {
        if (domain == null) return null;
        AsistenciaClaseEntity entity = new AsistenciaClaseEntity();
        entity.setId(domain.getId());
        entity.setSesionClaseId(domain.getSesionClaseId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setTipoAsistencia(domain.getTipoAsistencia());
        entity.setObservacion(domain.getObservacion());
        entity.setJustificacion(domain.getJustificacion());
        entity.setMinutosTarde(domain.getMinutosTarde());
        entity.setRegistradaPorUsuarioId(domain.getRegistradaPorUsuarioId());
        entity.setFechaRegistro(domain.getFechaRegistro());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
