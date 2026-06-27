package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.EscalaValorativa;
import backend.academic.infrastructure.persistence.entity.EscalaValorativaEntity;

@Component
public class EscalaValorativaMapper {

    public EscalaValorativa toDomain(EscalaValorativaEntity entity) {
        if (entity == null) return null;
        EscalaValorativa domain = new EscalaValorativa();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setNombre(entity.getNombre());
        domain.setAbreviatura(entity.getAbreviatura());
        domain.setNotaMinima(entity.getNotaMinima());
        domain.setNotaMaxima(entity.getNotaMaxima());
        domain.setAprueba(entity.getAprueba());
        domain.setOrden(entity.getOrden());
        domain.setActiva(entity.getActiva());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public EscalaValorativaEntity toEntity(EscalaValorativa domain) {
        if (domain == null) return null;
        EscalaValorativaEntity entity = new EscalaValorativaEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setNombre(domain.getNombre());
        entity.setAbreviatura(domain.getAbreviatura());
        entity.setNotaMinima(domain.getNotaMinima());
        entity.setNotaMaxima(domain.getNotaMaxima());
        entity.setAprueba(domain.getAprueba());
        entity.setOrden(domain.getOrden());
        entity.setActiva(domain.getActiva());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
