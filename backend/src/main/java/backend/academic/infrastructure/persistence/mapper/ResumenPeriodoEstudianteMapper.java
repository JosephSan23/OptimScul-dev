package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.ResumenPeriodoEstudiante;
import backend.academic.infrastructure.persistence.entity.ResumenPeriodoEstudianteEntity;

@Component
public class ResumenPeriodoEstudianteMapper {

    public ResumenPeriodoEstudiante toDomain(ResumenPeriodoEstudianteEntity entity) {
        if (entity == null) return null;
        ResumenPeriodoEstudiante domain = new ResumenPeriodoEstudiante();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setPeriodoAcademicoId(entity.getPeriodoAcademicoId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setCargaAcademicaId(entity.getCargaAcademicaId());
        domain.setNotaFinal(entity.getNotaFinal());
        domain.setObservacion(entity.getObservacion());
        domain.setRecuperacionAplica(entity.getRecuperacionAplica());
        domain.setNotaRecuperacion(entity.getNotaRecuperacion());
        domain.setNotaDefinitiva(entity.getNotaDefinitiva());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public ResumenPeriodoEstudianteEntity toEntity(ResumenPeriodoEstudiante domain) {
        if (domain == null) return null;
        ResumenPeriodoEstudianteEntity entity = new ResumenPeriodoEstudianteEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setPeriodoAcademicoId(domain.getPeriodoAcademicoId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setCargaAcademicaId(domain.getCargaAcademicaId());
        entity.setNotaFinal(domain.getNotaFinal());
        entity.setObservacion(domain.getObservacion());
        entity.setRecuperacionAplica(domain.getRecuperacionAplica());
        entity.setNotaRecuperacion(domain.getNotaRecuperacion());
        entity.setNotaDefinitiva(domain.getNotaDefinitiva());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
