package backend.people.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.people.domain.model.Acudiente;
import backend.people.infrastructure.persistence.entity.AcudienteEntity;

@Component
public class AcudienteMapper {

    public Acudiente toDomain(AcudienteEntity entity) {
        if (entity == null) return null;
        Acudiente domain = new Acudiente();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setPersonaId(entity.getPersonaId());
        domain.setOcupacion(entity.getOcupacion());
        domain.setEmpresa(entity.getEmpresa());
        domain.setEstado(entity.getEstado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public AcudienteEntity toEntity(Acudiente domain) {
        if (domain == null) return null;
        AcudienteEntity entity = new AcudienteEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setPersonaId(domain.getPersonaId());
        entity.setOcupacion(domain.getOcupacion());
        entity.setEmpresa(domain.getEmpresa());
        entity.setEstado(domain.getEstado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
