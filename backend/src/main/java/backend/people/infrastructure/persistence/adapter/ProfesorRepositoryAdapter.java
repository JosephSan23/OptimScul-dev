package backend.people.infrastructure.persistence.adapter;

import backend.people.application.port.ProfesorRepository;
import backend.people.domain.model.Profesor;
import backend.people.infrastructure.persistence.ProfesorJpaRepository;
import backend.people.infrastructure.persistence.mapper.ProfesorMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProfesorRepositoryAdapter implements ProfesorRepository {
    private final ProfesorJpaRepository jpa;
    private final ProfesorMapper mapper;

    public ProfesorRepositoryAdapter(ProfesorJpaRepository jpa, ProfesorMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Profesor save(Profesor e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<Profesor> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Profesor> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<Profesor> findByInstitucionId(UUID inst) {
        return jpa.findByInstitucionId(inst).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Profesor> findByInstitucionIdAndPersonaId(UUID inst, UUID personaId) {
        return jpa.findByInstitucionIdAndPersonaId(inst, personaId).map(mapper::toDomain);
    }
}