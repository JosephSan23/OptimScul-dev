package backend.people.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.people.domain.model.EstudianteAcudiente;
import backend.people.infrastructure.persistence.entity.EstudianteAcudienteEntity;

@Component
public class EstudianteAcudienteMapper {

    public EstudianteAcudiente toDomain(EstudianteAcudienteEntity entity) {
        if (entity == null) return null;
        EstudianteAcudiente domain = new EstudianteAcudiente();
        domain.setId(entity.getId());
        domain.setEstudianteId(entity.getEstudianteId());
        domain.setAcudienteId(entity.getAcudienteId());
        domain.setParentesco(entity.getParentesco());
        domain.setParentescoId(entity.getParentescoId());
        domain.setEsPrincipal(entity.getEsPrincipal());
        domain.setAutorizadoRecogida(entity.getAutorizadoRecogida());
        domain.setAutorizadoInfoAcademica(entity.getAutorizadoInfoAcademica());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public EstudianteAcudienteEntity toEntity(EstudianteAcudiente domain) {
        if (domain == null) return null;
        EstudianteAcudienteEntity entity = new EstudianteAcudienteEntity();
        entity.setId(domain.getId());
        entity.setEstudianteId(domain.getEstudianteId());
        entity.setAcudienteId(domain.getAcudienteId());
        entity.setParentesco(domain.getParentesco());
        entity.setParentescoId(domain.getParentescoId());
        entity.setEsPrincipal(domain.getEsPrincipal());
        entity.setAutorizadoRecogida(domain.getAutorizadoRecogida());
        entity.setAutorizadoInfoAcademica(domain.getAutorizadoInfoAcademica());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
