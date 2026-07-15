package backend.people.infrastructure.persistence.adapter;

import backend.people.application.port.AcudienteRepository;
import backend.people.domain.model.Acudiente;
import backend.people.infrastructure.persistence.AcudienteJpaRepository;
import backend.people.infrastructure.persistence.mapper.AcudienteMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AcudienteRepositoryAdapter implements AcudienteRepository {
    private final AcudienteJpaRepository jpa;
    private final AcudienteMapper mapper;

    public AcudienteRepositoryAdapter(AcudienteJpaRepository jpa, AcudienteMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Acudiente save(Acudiente a) {
        return mapper.toDomain(jpa.save(mapper.toEntity(a)));
    }

    @Override
    public Optional<Acudiente> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Acudiente> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public Optional<Acudiente> findByPersonaId(UUID personaId) {
        return jpa.findByPersonaId(personaId).map(mapper::toDomain);
    }
}