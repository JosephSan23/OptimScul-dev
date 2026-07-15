package backend.people.infrastructure.persistence.adapter;

import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Persona;
import backend.people.infrastructure.persistence.PersonaJpaRepository;
import backend.people.infrastructure.persistence.mapper.PersonaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PersonaRepositoryAdapter implements PersonaRepository {

    private final PersonaJpaRepository jpaRepository;
    private final PersonaMapper mapper;

    public PersonaRepositoryAdapter(PersonaJpaRepository jpaRepository, PersonaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Persona save(Persona entity) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(entity)));
    }

    @Override
    public Optional<Persona> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Persona> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNumeroDocumento(String numeroDocumento) {
        return jpaRepository.existsByNumeroDocumento(numeroDocumento);
    }

    @Override
    public Optional<Persona> findByNumeroDocumento(String numeroDocumento) {
        return jpaRepository.findByNumeroDocumento(numeroDocumento).map(mapper::toDomain);
    }
}