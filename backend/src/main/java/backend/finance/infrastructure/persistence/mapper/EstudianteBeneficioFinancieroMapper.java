package backend.finance.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.finance.domain.model.EstudianteBeneficioFinanciero;
import backend.finance.infrastructure.persistence.entity.EstudianteBeneficioFinancieroEntity;

@Component
public class EstudianteBeneficioFinancieroMapper {

    public EstudianteBeneficioFinanciero toDomain(EstudianteBeneficioFinancieroEntity entity) {
        if (entity == null) return null;
        EstudianteBeneficioFinanciero domain = new EstudianteBeneficioFinanciero();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setBeneficioFinancieroId(entity.getBeneficioFinancieroId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setPeriodoAcademicoId(entity.getPeriodoAcademicoId());
        domain.setFechaInicio(entity.getFechaInicio());
        domain.setFechaFin(entity.getFechaFin());
        domain.setEstado(entity.getEstado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setAprobadoPorUsuarioId(entity.getAprobadoPorUsuarioId());
        domain.setFechaAprobacion(entity.getFechaAprobacion());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public EstudianteBeneficioFinancieroEntity toEntity(EstudianteBeneficioFinanciero domain) {
        if (domain == null) return null;
        EstudianteBeneficioFinancieroEntity entity = new EstudianteBeneficioFinancieroEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setBeneficioFinancieroId(domain.getBeneficioFinancieroId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setPeriodoAcademicoId(domain.getPeriodoAcademicoId());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setFechaFin(domain.getFechaFin());
        entity.setEstado(domain.getEstado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setAprobadoPorUsuarioId(domain.getAprobadoPorUsuarioId());
        entity.setFechaAprobacion(domain.getFechaAprobacion());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
