package backend.people.infrastructure.persistence.adapter;

import backend.people.application.port.EstudianteAcudienteRepository;
import backend.people.domain.model.EstudianteAcudiente;
import backend.people.infrastructure.persistence.EstudianteAcudienteJpaRepository;
import backend.people.infrastructure.persistence.mapper.EstudianteAcudienteMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EstudianteAcudienteRepositoryAdapter implements EstudianteAcudienteRepository {
    private final EstudianteAcudienteJpaRepository jpa;
    private final EstudianteAcudienteMapper mapper;

    public EstudianteAcudienteRepositoryAdapter(EstudianteAcudienteJpaRepository jpa,
            EstudianteAcudienteMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public EstudianteAcudiente save(EstudianteAcudiente x) {
        return mapper.toDomain(jpa.save(mapper.toEntity(x)));
    }

    @Override
    public Optional<EstudianteAcudiente> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<EstudianteAcudiente> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<EstudianteAcudiente> findByEstudianteId(UUID estudianteId) {
        return jpa.findByEstudianteId(estudianteId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByEstudianteIdAndAcudienteId(UUID estudianteId, UUID acudienteId) {
        return jpa.existsByEstudianteIdAndAcudienteId(estudianteId, acudienteId);
    }
}