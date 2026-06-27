package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.PeriodoAcademico;
import backend.academic.infrastructure.persistence.entity.PeriodoAcademicoEntity;

@Component
public class PeriodoAcademicoMapper {

    public PeriodoAcademico toDomain(PeriodoAcademicoEntity entity) {
        if (entity == null) return null;
        PeriodoAcademico domain = new PeriodoAcademico();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setNumero(entity.getNumero());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setFechaInicio(entity.getFechaInicio());
        domain.setFechaFin(entity.getFechaFin());
        domain.setPeso(entity.getPeso());
        domain.setEstado(entity.getEstado());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public PeriodoAcademicoEntity toEntity(PeriodoAcademico domain) {
        if (domain == null) return null;
        PeriodoAcademicoEntity entity = new PeriodoAcademicoEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setNumero(domain.getNumero());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setFechaFin(domain.getFechaFin());
        entity.setPeso(domain.getPeso());
        entity.setEstado(domain.getEstado());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
