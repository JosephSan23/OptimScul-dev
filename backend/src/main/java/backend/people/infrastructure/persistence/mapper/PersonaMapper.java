package backend.people.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.people.domain.model.Persona;
import backend.people.infrastructure.persistence.entity.PersonaEntity;

@Component
public class PersonaMapper {

    public Persona toDomain(PersonaEntity entity) {
        if (entity == null) return null;
        Persona domain = new Persona();
        domain.setId(entity.getId());
        domain.setTipoDocumento(entity.getTipoDocumento());
        domain.setNumeroDocumento(entity.getNumeroDocumento());
        domain.setPrimerNombre(entity.getPrimerNombre());
        domain.setSegundoNombre(entity.getSegundoNombre());
        domain.setPrimerApellido(entity.getPrimerApellido());
        domain.setSegundoApellido(entity.getSegundoApellido());
        domain.setFechaNacimiento(entity.getFechaNacimiento());
        domain.setSexo(entity.getSexo());
        domain.setNacionalidad(entity.getNacionalidad());
        domain.setTelefono(entity.getTelefono());
        domain.setTelefonoAlternativo(entity.getTelefonoAlternativo());
        domain.setCorreo(entity.getCorreo());
        domain.setDireccion(entity.getDireccion());
        domain.setBarrio(entity.getBarrio());
        domain.setCiudad(entity.getCiudad());
        domain.setDepartamento(entity.getDepartamento());
        domain.setPais(entity.getPais());
        domain.setFotoUrl(entity.getFotoUrl());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public PersonaEntity toEntity(Persona domain) {
        if (domain == null) return null;
        PersonaEntity entity = new PersonaEntity();
        entity.setId(domain.getId());
        entity.setTipoDocumento(domain.getTipoDocumento());
        entity.setNumeroDocumento(domain.getNumeroDocumento());
        entity.setPrimerNombre(domain.getPrimerNombre());
        entity.setSegundoNombre(domain.getSegundoNombre());
        entity.setPrimerApellido(domain.getPrimerApellido());
        entity.setSegundoApellido(domain.getSegundoApellido());
        entity.setFechaNacimiento(domain.getFechaNacimiento());
        entity.setSexo(domain.getSexo());
        entity.setNacionalidad(domain.getNacionalidad());
        entity.setTelefono(domain.getTelefono());
        entity.setTelefonoAlternativo(domain.getTelefonoAlternativo());
        entity.setCorreo(domain.getCorreo());
        entity.setDireccion(domain.getDireccion());
        entity.setBarrio(domain.getBarrio());
        entity.setCiudad(domain.getCiudad());
        entity.setDepartamento(domain.getDepartamento());
        entity.setPais(domain.getPais());
        entity.setFotoUrl(domain.getFotoUrl());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
