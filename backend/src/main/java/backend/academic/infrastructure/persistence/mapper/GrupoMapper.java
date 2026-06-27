package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.Grupo;
import backend.academic.infrastructure.persistence.entity.GrupoEntity;

@Component
public class GrupoMapper {

    public Grupo toDomain(GrupoEntity entity) {
        if (entity == null) return null;
        Grupo domain = new Grupo();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setSedeId(entity.getSedeId());
        domain.setJornadaId(entity.getJornadaId());
        domain.setGradoId(entity.getGradoId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setCupoMaximo(entity.getCupoMaximo());
        domain.setEstado(entity.getEstado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public GrupoEntity toEntity(Grupo domain) {
        if (domain == null) return null;
        GrupoEntity entity = new GrupoEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setSedeId(domain.getSedeId());
        entity.setJornadaId(domain.getJornadaId());
        entity.setGradoId(domain.getGradoId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setCupoMaximo(domain.getCupoMaximo());
        entity.setEstado(domain.getEstado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
