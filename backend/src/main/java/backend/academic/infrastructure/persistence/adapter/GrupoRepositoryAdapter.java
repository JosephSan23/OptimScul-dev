package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.GrupoRepository;
import backend.academic.domain.model.Grupo;
import backend.academic.infrastructure.persistence.GrupoJpaRepository;
import backend.academic.infrastructure.persistence.mapper.GrupoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GrupoRepositoryAdapter implements GrupoRepository {
    private final GrupoJpaRepository jpa;
    private final GrupoMapper mapper;

    public GrupoRepositoryAdapter(GrupoJpaRepository jpa, GrupoMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Grupo save(Grupo e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<Grupo> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Grupo> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<Grupo> findByGradoId(UUID gradoId) {
        return jpa.findByGradoIdOrderByCodigoAsc(gradoId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByInstitucionIdAndAnioLectivoIdAndCodigo(UUID inst, UUID anioId, String codigo) {
        return jpa.existsByInstitucionIdAndAnioLectivoIdAndCodigoIgnoreCase(inst, anioId, codigo);
    }

    @Override
    public java.util.Map<UUID, Long> contarPorGradoDeInstitucion(UUID institucionId) {
        return jpa.contarPorGrado(institucionId).stream()
                .collect(java.util.stream.Collectors.toMap(
                        GrupoJpaRepository.ConteoPorGrado::getGradoId,
                        GrupoJpaRepository.ConteoPorGrado::getTotal));
    }

    @Override
    public List<Grupo> findByInstitucionIdAndAnioLectivoId(UUID inst, UUID anioId) {
        return jpa.findByInstitucionIdAndAnioLectivoIdOrderByCodigoAsc(inst, anioId).stream().map(mapper::toDomain).toList();
    }
}