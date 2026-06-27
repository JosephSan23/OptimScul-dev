package backend.finance.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.finance.domain.model.BeneficioFinanciero;
import backend.finance.infrastructure.persistence.entity.BeneficioFinancieroEntity;

@Component
public class BeneficioFinancieroMapper {

    public BeneficioFinanciero toDomain(BeneficioFinancieroEntity entity) {
        if (entity == null) return null;
        BeneficioFinanciero domain = new BeneficioFinanciero();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setTipo(entity.getTipo());
        domain.setPorcentaje(entity.getPorcentaje());
        domain.setValorFijo(entity.getValorFijo());
        domain.setConceptoCobroId(entity.getConceptoCobroId());
        domain.setActivo(entity.getActivo());
        domain.setRequiereAprobacion(entity.getRequiereAprobacion());
        domain.setAcumulable(entity.getAcumulable());
        domain.setPrioridad(entity.getPrioridad());
        domain.setModoAplicacion(entity.getModoAplicacion());
        domain.setPermiteConvivirConMejorBeneficio(entity.getPermiteConvivirConMejorBeneficio());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public BeneficioFinancieroEntity toEntity(BeneficioFinanciero domain) {
        if (domain == null) return null;
        BeneficioFinancieroEntity entity = new BeneficioFinancieroEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setTipo(domain.getTipo());
        entity.setPorcentaje(domain.getPorcentaje());
        entity.setValorFijo(domain.getValorFijo());
        entity.setConceptoCobroId(domain.getConceptoCobroId());
        entity.setActivo(domain.getActivo());
        entity.setRequiereAprobacion(domain.getRequiereAprobacion());
        entity.setAcumulable(domain.getAcumulable());
        entity.setPrioridad(domain.getPrioridad());
        entity.setModoAplicacion(domain.getModoAplicacion());
        entity.setPermiteConvivirConMejorBeneficio(domain.getPermiteConvivirConMejorBeneficio());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
