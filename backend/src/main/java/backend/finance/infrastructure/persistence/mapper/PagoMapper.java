package backend.finance.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.finance.domain.model.Pago;
import backend.finance.infrastructure.persistence.entity.PagoEntity;

@Component
public class PagoMapper {

    public Pago toDomain(PagoEntity entity) {
        if (entity == null) return null;
        Pago domain = new Pago();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCuentaCobroId(entity.getCuentaCobroId());
        domain.setFechaPago(entity.getFechaPago());
        domain.setValor(entity.getValor());
        domain.setMetodo(entity.getMetodo());
        domain.setReferenciaPago(entity.getReferenciaPago());
        domain.setComprobanteUrl(entity.getComprobanteUrl());
        domain.setObservaciones(entity.getObservaciones());
        domain.setRegistradoPorUsuarioId(entity.getRegistradoPorUsuarioId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public PagoEntity toEntity(Pago domain) {
        if (domain == null) return null;
        PagoEntity entity = new PagoEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCuentaCobroId(domain.getCuentaCobroId());
        entity.setFechaPago(domain.getFechaPago());
        entity.setValor(domain.getValor());
        entity.setMetodo(domain.getMetodo());
        entity.setReferenciaPago(domain.getReferenciaPago());
        entity.setComprobanteUrl(domain.getComprobanteUrl());
        entity.setObservaciones(domain.getObservaciones());
        entity.setRegistradoPorUsuarioId(domain.getRegistradoPorUsuarioId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
