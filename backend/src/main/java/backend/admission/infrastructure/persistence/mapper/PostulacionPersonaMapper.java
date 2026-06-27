package backend.admission.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.admission.domain.model.PostulacionPersona;
import backend.admission.infrastructure.persistence.entity.PostulacionPersonaEntity;

@Component
public class PostulacionPersonaMapper {

    public PostulacionPersona toDomain(PostulacionPersonaEntity entity) {
        if (entity == null) return null;
        PostulacionPersona domain = new PostulacionPersona();
        domain.setId(entity.getId());
        domain.setPostulacionId(entity.getPostulacionId());
        domain.setTipoDocumento(entity.getTipoDocumento());
        domain.setNumeroDocumento(entity.getNumeroDocumento());
        domain.setPrimerNombre(entity.getPrimerNombre());
        domain.setSegundoNombre(entity.getSegundoNombre());
        domain.setPrimerApellido(entity.getPrimerApellido());
        domain.setSegundoApellido(entity.getSegundoApellido());
        domain.setFechaNacimiento(entity.getFechaNacimiento());
        domain.setSexo(entity.getSexo());
        domain.setNacionalidad(entity.getNacionalidad());
        domain.setCorreo(entity.getCorreo());
        domain.setTelefono(entity.getTelefono());
        domain.setDireccion(entity.getDireccion());
        domain.setCiudad(entity.getCiudad());
        domain.setDepartamento(entity.getDepartamento());
        domain.setPais(entity.getPais());
        domain.setColegioProcedencia(entity.getColegioProcedencia());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public PostulacionPersonaEntity toEntity(PostulacionPersona domain) {
        if (domain == null) return null;
        PostulacionPersonaEntity entity = new PostulacionPersonaEntity();
        entity.setId(domain.getId());
        entity.setPostulacionId(domain.getPostulacionId());
        entity.setTipoDocumento(domain.getTipoDocumento());
        entity.setNumeroDocumento(domain.getNumeroDocumento());
        entity.setPrimerNombre(domain.getPrimerNombre());
        entity.setSegundoNombre(domain.getSegundoNombre());
        entity.setPrimerApellido(domain.getPrimerApellido());
        entity.setSegundoApellido(domain.getSegundoApellido());
        entity.setFechaNacimiento(domain.getFechaNacimiento());
        entity.setSexo(domain.getSexo());
        entity.setNacionalidad(domain.getNacionalidad());
        entity.setCorreo(domain.getCorreo());
        entity.setTelefono(domain.getTelefono());
        entity.setDireccion(domain.getDireccion());
        entity.setCiudad(domain.getCiudad());
        entity.setDepartamento(domain.getDepartamento());
        entity.setPais(domain.getPais());
        entity.setColegioProcedencia(domain.getColegioProcedencia());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
