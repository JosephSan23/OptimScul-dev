package backend.finance.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.finance.domain.model.ConceptoCobro;
import backend.finance.infrastructure.persistence.entity.ConceptoCobroEntity;

@Component
public class ConceptoCobroMapper {

    public ConceptoCobro toDomain(ConceptoCobroEntity entity) {
        if (entity == null) return null;
        ConceptoCobro domain = new ConceptoCobro();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setCategoria(entity.getCategoria());
        domain.setValorBase(entity.getValorBase());
        domain.setEsRecurrente(entity.getEsRecurrente());
        domain.setRequiereVencimiento(entity.getRequiereVencimiento());
        domain.setPermiteDescuento(entity.getPermiteDescuento());
        domain.setPermiteRecargo(entity.getPermiteRecargo());
        domain.setActivo(entity.getActivo());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public ConceptoCobroEntity toEntity(ConceptoCobro domain) {
        if (domain == null) return null;
        ConceptoCobroEntity entity = new ConceptoCobroEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setCategoria(domain.getCategoria());
        entity.setValorBase(domain.getValorBase());
        entity.setEsRecurrente(domain.getEsRecurrente());
        entity.setRequiereVencimiento(domain.getRequiereVencimiento());
        entity.setPermiteDescuento(domain.getPermiteDescuento());
        entity.setPermiteRecargo(domain.getPermiteRecargo());
        entity.setActivo(domain.getActivo());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
