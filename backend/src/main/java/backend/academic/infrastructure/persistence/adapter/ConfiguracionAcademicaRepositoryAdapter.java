package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.ConfiguracionAcademicaRepository;
import backend.academic.domain.model.ConfiguracionAcademica;
import backend.academic.infrastructure.persistence.ConfiguracionAcademicaJpaRepository;
import backend.academic.infrastructure.persistence.mapper.ConfiguracionAcademicaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ConfiguracionAcademicaRepositoryAdapter implements ConfiguracionAcademicaRepository {
    private final ConfiguracionAcademicaJpaRepository jpa;
    private final ConfiguracionAcademicaMapper mapper;

    public ConfiguracionAcademicaRepositoryAdapter(ConfiguracionAcademicaJpaRepository jpa,
            ConfiguracionAcademicaMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public ConfiguracionAcademica save(ConfiguracionAcademica e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<ConfiguracionAcademica> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ConfiguracionAcademica> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public Optional<ConfiguracionAcademica> findByInstitucionId(UUID inst) {
        return jpa.findByInstitucionId(inst).map(mapper::toDomain);
    }
}