package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.HorarioCarga;
import backend.academic.infrastructure.persistence.entity.HorarioCargaEntity;

@Component
public class HorarioCargaMapper {

    public HorarioCarga toDomain(HorarioCargaEntity entity) {
        if (entity == null) return null;
        HorarioCarga domain = new HorarioCarga();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCargaAcademicaId(entity.getCargaAcademicaId());
        domain.setSedeId(entity.getSedeId());
        domain.setDiaSemana(entity.getDiaSemana());
        domain.setHoraInicio(entity.getHoraInicio());
        domain.setHoraFin(entity.getHoraFin());
        domain.setAula(entity.getAula());
        domain.setActivo(entity.getActivo());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public HorarioCargaEntity toEntity(HorarioCarga domain) {
        if (domain == null) return null;
        HorarioCargaEntity entity = new HorarioCargaEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCargaAcademicaId(domain.getCargaAcademicaId());
        entity.setSedeId(domain.getSedeId());
        entity.setDiaSemana(domain.getDiaSemana());
        entity.setHoraInicio(domain.getHoraInicio());
        entity.setHoraFin(domain.getHoraFin());
        entity.setAula(domain.getAula());
        entity.setActivo(domain.getActivo());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
