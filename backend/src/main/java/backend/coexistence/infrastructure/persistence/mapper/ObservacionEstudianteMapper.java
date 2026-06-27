package backend.coexistence.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.coexistence.domain.model.ObservacionEstudiante;
import backend.coexistence.infrastructure.persistence.entity.ObservacionEstudianteEntity;

@Component
public class ObservacionEstudianteMapper {

    public ObservacionEstudiante toDomain(ObservacionEstudianteEntity entity) {
        if (entity == null) return null;
        ObservacionEstudiante domain = new ObservacionEstudiante();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setPeriodoAcademicoId(entity.getPeriodoAcademicoId());
        domain.setGrupoId(entity.getGrupoId());
        domain.setCargaAcademicaId(entity.getCargaAcademicaId());
        domain.setTipoObservacionId(entity.getTipoObservacionId());
        domain.setTitulo(entity.getTitulo());
        domain.setDescripcion(entity.getDescripcion());
        domain.setFechaEvento(entity.getFechaEvento());
        domain.setSeveridad(entity.getSeveridad());
        domain.setEstado(entity.getEstado());
        domain.setVisibleAcudiente(entity.getVisibleAcudiente());
        domain.setRequiereSeguimiento(entity.getRequiereSeguimiento());
        domain.setCerradaEn(entity.getCerradaEn());
        domain.setCreadaPorUsuarioId(entity.getCreadaPorUsuarioId());
        domain.setCerradaPorUsuarioId(entity.getCerradaPorUsuarioId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public ObservacionEstudianteEntity toEntity(ObservacionEstudiante domain) {
        if (domain == null) return null;
        ObservacionEstudianteEntity entity = new ObservacionEstudianteEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setPeriodoAcademicoId(domain.getPeriodoAcademicoId());
        entity.setGrupoId(domain.getGrupoId());
        entity.setCargaAcademicaId(domain.getCargaAcademicaId());
        entity.setTipoObservacionId(domain.getTipoObservacionId());
        entity.setTitulo(domain.getTitulo());
        entity.setDescripcion(domain.getDescripcion());
        entity.setFechaEvento(domain.getFechaEvento());
        entity.setSeveridad(domain.getSeveridad());
        entity.setEstado(domain.getEstado());
        entity.setVisibleAcudiente(domain.getVisibleAcudiente());
        entity.setRequiereSeguimiento(domain.getRequiereSeguimiento());
        entity.setCerradaEn(domain.getCerradaEn());
        entity.setCreadaPorUsuarioId(domain.getCreadaPorUsuarioId());
        entity.setCerradaPorUsuarioId(domain.getCerradaPorUsuarioId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
