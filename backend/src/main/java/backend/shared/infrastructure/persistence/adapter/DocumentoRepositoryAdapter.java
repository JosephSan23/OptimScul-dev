package backend.shared.infrastructure.persistence.adapter;

import backend.shared.ModuloDocumento;
import backend.shared.application.port.DocumentoRepository;
import backend.shared.domain.model.Documento;
import backend.shared.infrastructure.persistence.DocumentoJpaRepository;
import backend.shared.infrastructure.persistence.mapper.DocumentoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DocumentoRepositoryAdapter implements DocumentoRepository {

    private final DocumentoJpaRepository jpa;
    private final DocumentoMapper mapper;

    public DocumentoRepositoryAdapter(DocumentoJpaRepository jpa, DocumentoMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Documento save(Documento d) {
        return mapper.toDomain(jpa.save(mapper.toEntity(d)));
    }

    @Override
    public Optional<Documento> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Documento> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<Documento> findByModuloAndEntidadId(ModuloDocumento modulo, UUID entidadId) {
        return jpa.findByModuloAndEntidadIdOrderByCreatedAtAsc(modulo, entidadId)
                .stream().map(mapper::toDomain).toList();
    }
}