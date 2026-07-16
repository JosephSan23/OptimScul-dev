package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.GradoRepository;
import backend.academic.domain.model.Grado;
import backend.academic.infrastructure.persistence.GradoJpaRepository;
import backend.academic.infrastructure.persistence.mapper.GradoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GradoRepositoryAdapter implements GradoRepository {
    private final GradoJpaRepository jpa;
    private final GradoMapper mapper;

    public GradoRepositoryAdapter(GradoJpaRepository jpa, GradoMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Grado save(Grado e) { return mapper.toDomain(jpa.save(mapper.toEntity(e))); }

    @Override
    public Optional<Grado> findById(UUID id) { return jpa.findById(id).map(mapper::toDomain); }

    @Override
    public List<Grado> findAll() { return jpa.findAll().stream().map(mapper::toDomain).toList(); }

    @Override
    public void deleteById(UUID id) { jpa.deleteById(id); }

    @Override
    public List<Grado> findByInstitucionId(UUID inst) {
        return jpa.findByInstitucionIdOrderByOrdenAsc(inst).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByInstitucionIdAndCodigo(UUID inst, String codigo) {
        return jpa.existsByInstitucionIdAndCodigoIgnoreCase(inst, codigo);
    }
}