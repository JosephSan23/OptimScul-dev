package backend.admission.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.admission.domain.model.PostulacionAcudiente;
import backend.admission.infrastructure.persistence.entity.PostulacionAcudienteEntity;

@Component
public class PostulacionAcudienteMapper {

    public PostulacionAcudiente toDomain(PostulacionAcudienteEntity entity) {
        if (entity == null) return null;
        PostulacionAcudiente domain = new PostulacionAcudiente();
        domain.setId(entity.getId());
        domain.setPostulacionId(entity.getPostulacionId());
        domain.setTipoDocumento(entity.getTipoDocumento());
        domain.setNumeroDocumento(entity.getNumeroDocumento());
        domain.setPrimerNombre(entity.getPrimerNombre());
        domain.setSegundoNombre(entity.getSegundoNombre());
        domain.setPrimerApellido(entity.getPrimerApellido());
        domain.setSegundoApellido(entity.getSegundoApellido());
        domain.setParentesco(entity.getParentesco());
        domain.setTelefono(entity.getTelefono());
        domain.setTelefonoAlternativo(entity.getTelefonoAlternativo());
        domain.setCorreo(entity.getCorreo());
        domain.setDireccion(entity.getDireccion());
        domain.setOcupacion(entity.getOcupacion());
        domain.setEmpresa(entity.getEmpresa());
        domain.setEsPrincipal(entity.getEsPrincipal());
        domain.setAutorizadoRecogida(entity.getAutorizadoRecogida());
        domain.setAutorizadoInfoAcademica(entity.getAutorizadoInfoAcademica());
        domain.setObservaciones(entity.getObservaciones());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public PostulacionAcudienteEntity toEntity(PostulacionAcudiente domain) {
        if (domain == null) return null;
        PostulacionAcudienteEntity entity = new PostulacionAcudienteEntity();
        entity.setId(domain.getId());
        entity.setPostulacionId(domain.getPostulacionId());
        entity.setTipoDocumento(domain.getTipoDocumento());
        entity.setNumeroDocumento(domain.getNumeroDocumento());
        entity.setPrimerNombre(domain.getPrimerNombre());
        entity.setSegundoNombre(domain.getSegundoNombre());
        entity.setPrimerApellido(domain.getPrimerApellido());
        entity.setSegundoApellido(domain.getSegundoApellido());
        entity.setParentesco(domain.getParentesco());
        entity.setTelefono(domain.getTelefono());
        entity.setTelefonoAlternativo(domain.getTelefonoAlternativo());
        entity.setCorreo(domain.getCorreo());
        entity.setDireccion(domain.getDireccion());
        entity.setOcupacion(domain.getOcupacion());
        entity.setEmpresa(domain.getEmpresa());
        entity.setEsPrincipal(domain.getEsPrincipal());
        entity.setAutorizadoRecogida(domain.getAutorizadoRecogida());
        entity.setAutorizadoInfoAcademica(domain.getAutorizadoInfoAcademica());
        entity.setObservaciones(domain.getObservaciones());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
