package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.Grado;
import backend.academic.infrastructure.persistence.entity.GradoEntity;

@Component
public class GradoMapper {

    public Grado toDomain(GradoEntity entity) {
        if (entity == null) return null;
        Grado domain = new Grado();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setNivel(entity.getNivel());
        domain.setOrden(entity.getOrden());
        domain.setEstado(entity.getEstado());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public GradoEntity toEntity(Grado domain) {
        if (domain == null) return null;
        GradoEntity entity = new GradoEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setNivel(domain.getNivel());
        entity.setOrden(domain.getOrden());
        entity.setEstado(domain.getEstado());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
