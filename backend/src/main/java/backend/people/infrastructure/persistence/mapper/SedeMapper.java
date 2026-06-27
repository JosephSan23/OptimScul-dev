package backend.people.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.people.domain.model.Sede;
import backend.people.infrastructure.persistence.entity.SedeEntity;

@Component
public class SedeMapper {

    public Sede toDomain(SedeEntity entity) {
        if (entity == null) return null;
        Sede domain = new Sede();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setDireccion(entity.getDireccion());
        domain.setTelefono(entity.getTelefono());
        domain.setCorreo(entity.getCorreo());
        domain.setCiudad(entity.getCiudad());
        domain.setDepartamento(entity.getDepartamento());
        domain.setPais(entity.getPais());
        domain.setPrincipal(entity.getPrincipal());
        domain.setEstado(entity.getEstado());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public SedeEntity toEntity(Sede domain) {
        if (domain == null) return null;
        SedeEntity entity = new SedeEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setDireccion(domain.getDireccion());
        entity.setTelefono(domain.getTelefono());
        entity.setCorreo(domain.getCorreo());
        entity.setCiudad(domain.getCiudad());
        entity.setDepartamento(domain.getDepartamento());
        entity.setPais(domain.getPais());
        entity.setPrincipal(domain.getPrincipal());
        entity.setEstado(domain.getEstado());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
