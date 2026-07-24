package backend.people.infrastructure.persistence.adapter;

import backend.people.application.port.EstudianteRepository;
import backend.people.domain.model.Estudiante;
import backend.people.infrastructure.persistence.EstudianteJpaRepository;
import backend.people.infrastructure.persistence.mapper.EstudianteMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EstudianteRepositoryAdapter implements EstudianteRepository {
    private final EstudianteJpaRepository jpa;
    private final EstudianteMapper mapper;

    public EstudianteRepositoryAdapter(EstudianteJpaRepository jpa, EstudianteMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Estudiante save(Estudiante e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<Estudiante> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Estudiante> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<Estudiante> findByInstitucionId(UUID inst) {
        return jpa.findByInstitucionId(inst).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Estudiante> findByInstitucionIdAndPersonaId(UUID inst, UUID personaId) {
        return jpa.findByInstitucionIdAndPersonaId(inst, personaId).map(mapper::toDomain);
    }

}