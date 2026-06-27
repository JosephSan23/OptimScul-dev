package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.SesionClase;
import backend.academic.infrastructure.persistence.entity.SesionClaseEntity;

@Component
public class SesionClaseMapper {

    public SesionClase toDomain(SesionClaseEntity entity) {
        if (entity == null) return null;
        SesionClase domain = new SesionClase();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCargaAcademicaId(entity.getCargaAcademicaId());
        domain.setHorarioCargaId(entity.getHorarioCargaId());
        domain.setFecha(entity.getFecha());
        domain.setHoraInicio(entity.getHoraInicio());
        domain.setHoraFin(entity.getHoraFin());
        domain.setTema(entity.getTema());
        domain.setDescripcion(entity.getDescripcion());
        domain.setEstado(entity.getEstado());
        domain.setFueReprogramada(entity.getFueReprogramada());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public SesionClaseEntity toEntity(SesionClase domain) {
        if (domain == null) return null;
        SesionClaseEntity entity = new SesionClaseEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCargaAcademicaId(domain.getCargaAcademicaId());
        entity.setHorarioCargaId(domain.getHorarioCargaId());
        entity.setFecha(domain.getFecha());
        entity.setHoraInicio(domain.getHoraInicio());
        entity.setHoraFin(domain.getHoraFin());
        entity.setTema(domain.getTema());
        entity.setDescripcion(domain.getDescripcion());
        entity.setEstado(domain.getEstado());
        entity.setFueReprogramada(domain.getFueReprogramada());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
