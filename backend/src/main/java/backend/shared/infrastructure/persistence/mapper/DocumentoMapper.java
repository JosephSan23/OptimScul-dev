package backend.shared.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.shared.domain.model.Documento;
import backend.shared.infrastructure.persistence.entity.DocumentoEntity;

@Component
public class DocumentoMapper {

    public Documento toDomain(DocumentoEntity entity) {
        if (entity == null) return null;
        Documento domain = new Documento();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setModulo(entity.getModulo());
        domain.setEntidadId(entity.getEntidadId());
        domain.setNombreArchivo(entity.getNombreArchivo());
        domain.setNombreOriginal(entity.getNombreOriginal());
        domain.setUrlArchivo(entity.getUrlArchivo());
        domain.setMimeType(entity.getMimeType());
        domain.setTamanoBytes(entity.getTamanoBytes());
        domain.setDescripcion(entity.getDescripcion());
        domain.setSubidoPorUsuarioId(entity.getSubidoPorUsuarioId());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }

    public DocumentoEntity toEntity(Documento domain) {
        if (domain == null) return null;
        DocumentoEntity entity = new DocumentoEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setModulo(domain.getModulo());
        entity.setEntidadId(domain.getEntidadId());
        entity.setNombreArchivo(domain.getNombreArchivo());
        entity.setNombreOriginal(domain.getNombreOriginal());
        entity.setUrlArchivo(domain.getUrlArchivo());
        entity.setMimeType(domain.getMimeType());
        entity.setTamanoBytes(domain.getTamanoBytes());
        entity.setDescripcion(domain.getDescripcion());
        entity.setSubidoPorUsuarioId(domain.getSubidoPorUsuarioId());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
