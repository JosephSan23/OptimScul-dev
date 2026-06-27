package backend.finance.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.finance.domain.model.CuentaCobroBeneficio;
import backend.finance.infrastructure.persistence.entity.CuentaCobroBeneficioEntity;

@Component
public class CuentaCobroBeneficioMapper {

    public CuentaCobroBeneficio toDomain(CuentaCobroBeneficioEntity entity) {
        if (entity == null) return null;
        CuentaCobroBeneficio domain = new CuentaCobroBeneficio();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCuentaCobroId(entity.getCuentaCobroId());
        domain.setEstudianteBeneficioFinancieroId(entity.getEstudianteBeneficioFinancieroId());
        domain.setBeneficioFinancieroId(entity.getBeneficioFinancieroId());
        domain.setTipo(entity.getTipo());
        domain.setPorcentajeAplicado(entity.getPorcentajeAplicado());
        domain.setValorFijoAplicado(entity.getValorFijoAplicado());
        domain.setValorDescuentoAplicado(entity.getValorDescuentoAplicado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }

    public CuentaCobroBeneficioEntity toEntity(CuentaCobroBeneficio domain) {
        if (domain == null) return null;
        CuentaCobroBeneficioEntity entity = new CuentaCobroBeneficioEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCuentaCobroId(domain.getCuentaCobroId());
        entity.setEstudianteBeneficioFinancieroId(domain.getEstudianteBeneficioFinancieroId());
        entity.setBeneficioFinancieroId(domain.getBeneficioFinancieroId());
        entity.setTipo(domain.getTipo());
        entity.setPorcentajeAplicado(domain.getPorcentajeAplicado());
        entity.setValorFijoAplicado(domain.getValorFijoAplicado());
        entity.setValorDescuentoAplicado(domain.getValorDescuentoAplicado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
