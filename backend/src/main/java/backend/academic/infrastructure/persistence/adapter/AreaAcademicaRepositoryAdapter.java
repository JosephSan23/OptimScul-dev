package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.AreaAcademicaRepository;
import backend.academic.domain.model.AreaAcademica;
import backend.academic.infrastructure.persistence.AreaAcademicaJpaRepository;
import backend.academic.infrastructure.persistence.mapper.AreaAcademicaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AreaAcademicaRepositoryAdapter implements AreaAcademicaRepository {
    private final AreaAcademicaJpaRepository jpa;
    private final AreaAcademicaMapper mapper;

    public AreaAcademicaRepositoryAdapter(AreaAcademicaJpaRepository jpa, AreaAcademicaMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public AreaAcademica save(AreaAcademica e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<AreaAcademica> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<AreaAcademica> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<AreaAcademica> findByInstitucionId(UUID inst) {
        return jpa.findByInstitucionIdOrderByNombreAsc(inst).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByInstitucionIdAndCodigo(UUID inst, String codigo) {
        return jpa.existsByInstitucionIdAndCodigoIgnoreCase(inst, codigo);
    }
}