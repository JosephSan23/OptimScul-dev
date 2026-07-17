package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.AsignaturaRepository;
import backend.academic.domain.model.Asignatura;
import backend.academic.infrastructure.persistence.AsignaturaJpaRepository;
import backend.academic.infrastructure.persistence.mapper.AsignaturaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AsignaturaRepositoryAdapter implements AsignaturaRepository {
    private final AsignaturaJpaRepository jpa;
    private final AsignaturaMapper mapper;

    public AsignaturaRepositoryAdapter(AsignaturaJpaRepository jpa, AsignaturaMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Asignatura save(Asignatura e) { return mapper.toDomain(jpa.save(mapper.toEntity(e))); }

    @Override
    public Optional<Asignatura> findById(UUID id) { return jpa.findById(id).map(mapper::toDomain); }

    @Override
    public List<Asignatura> findAll() { return jpa.findAll().stream().map(mapper::toDomain).toList(); }

    @Override
    public void deleteById(UUID id) { jpa.deleteById(id); }

    @Override
    public List<Asignatura> findByInstitucionId(UUID inst) {
        return jpa.findByInstitucionIdOrderByNombreAsc(inst).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByInstitucionIdAndCodigo(UUID inst, String codigo) {
        return jpa.existsByInstitucionIdAndCodigoIgnoreCase(inst, codigo);
    }

    @Override
    public Map<UUID, Long> contarPorAreaDeInstitucion(UUID inst) {
        return jpa.contarPorArea(inst).stream()
                .collect(Collectors.toMap(
                        AsignaturaJpaRepository.ConteoPorArea::getAreaId,
                        AsignaturaJpaRepository.ConteoPorArea::getTotal));
    }
}