package backend.people.infrastructure.persistence.adapter;

import backend.people.application.port.SedeRepository;
import backend.people.domain.model.Sede;
import backend.people.infrastructure.persistence.SedeJpaRepository;
import backend.people.infrastructure.persistence.mapper.SedeMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SedeRepositoryAdapter implements SedeRepository {

    private final SedeJpaRepository jpaRepository;
    private final SedeMapper mapper;

    public SedeRepositoryAdapter(SedeJpaRepository jpaRepository, SedeMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Sede save(Sede entity) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(entity)));
    }

    @Override
    public Optional<Sede> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Sede> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Sede> findByInstitucionId(UUID institucionId) {
        return jpaRepository.findByInstitucionId(institucionId).stream().map(mapper::toDomain).toList();
    }
}