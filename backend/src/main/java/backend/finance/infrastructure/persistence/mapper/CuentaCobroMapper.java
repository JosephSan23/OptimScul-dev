package backend.finance.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.finance.domain.model.CuentaCobro;
import backend.finance.infrastructure.persistence.entity.CuentaCobroEntity;

@Component
public class CuentaCobroMapper {

    public CuentaCobro toDomain(CuentaCobroEntity entity) {
        if (entity == null) return null;
        CuentaCobro domain = new CuentaCobro();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setConceptoCobroId(entity.getConceptoCobroId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setPeriodoAcademicoId(entity.getPeriodoAcademicoId());
        domain.setCodigo(entity.getCodigo());
        domain.setFechaEmision(entity.getFechaEmision());
        domain.setFechaVencimiento(entity.getFechaVencimiento());
        domain.setDescripcion(entity.getDescripcion());
        domain.setValorBase(entity.getValorBase());
        domain.setDescuento(entity.getDescuento());
        domain.setRecargo(entity.getRecargo());
        domain.setTotal(entity.getTotal());
        domain.setSaldo(entity.getSaldo());
        domain.setEstado(entity.getEstado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setAnuladoPorUsuarioId(entity.getAnuladoPorUsuarioId());
        domain.setFechaAnulacion(entity.getFechaAnulacion());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public CuentaCobroEntity toEntity(CuentaCobro domain) {
        if (domain == null) return null;
        CuentaCobroEntity entity = new CuentaCobroEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setConceptoCobroId(domain.getConceptoCobroId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setPeriodoAcademicoId(domain.getPeriodoAcademicoId());
        entity.setCodigo(domain.getCodigo());
        entity.setFechaEmision(domain.getFechaEmision());
        entity.setFechaVencimiento(domain.getFechaVencimiento());
        entity.setDescripcion(domain.getDescripcion());
        entity.setValorBase(domain.getValorBase());
        entity.setDescuento(domain.getDescuento());
        entity.setRecargo(domain.getRecargo());
        entity.setTotal(domain.getTotal());
        entity.setSaldo(domain.getSaldo());
        entity.setEstado(domain.getEstado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setAnuladoPorUsuarioId(domain.getAnuladoPorUsuarioId());
        entity.setFechaAnulacion(domain.getFechaAnulacion());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
