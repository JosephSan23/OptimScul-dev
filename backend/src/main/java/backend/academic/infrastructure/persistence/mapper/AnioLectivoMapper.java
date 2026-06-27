package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.AnioLectivo;
import backend.academic.infrastructure.persistence.entity.AnioLectivoEntity;

@Component
public class AnioLectivoMapper {

    public AnioLectivo toDomain(AnioLectivoEntity entity) {
        if (entity == null) return null;
        AnioLectivo domain = new AnioLectivo();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setAnio(entity.getAnio());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setFechaInicio(entity.getFechaInicio());
        domain.setFechaFin(entity.getFechaFin());
        domain.setEstado(entity.getEstado());
        domain.setEsActual(entity.getEsActual());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public AnioLectivoEntity toEntity(AnioLectivo domain) {
        if (domain == null) return null;
        AnioLectivoEntity entity = new AnioLectivoEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setAnio(domain.getAnio());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setFechaFin(domain.getFechaFin());
        entity.setEstado(domain.getEstado());
        entity.setEsActual(domain.getEsActual());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
