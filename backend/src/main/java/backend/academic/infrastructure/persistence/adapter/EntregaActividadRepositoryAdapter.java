package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.EntregaActividadRepository;
import backend.academic.domain.model.EntregaActividad;
import backend.academic.infrastructure.persistence.EntregaActividadJpaRepository;
import backend.academic.infrastructure.persistence.mapper.EntregaActividadMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EntregaActividadRepositoryAdapter implements EntregaActividadRepository {

    private final EntregaActividadJpaRepository jpa;
    private final EntregaActividadMapper mapper;

    public EntregaActividadRepositoryAdapter(EntregaActividadJpaRepository jpa, EntregaActividadMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public EntregaActividad save(EntregaActividad e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<EntregaActividad> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<EntregaActividad> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public Optional<EntregaActividad> findByActividadIdAndEstudianteId(UUID actividadId, UUID estudianteId) {
        return jpa.findByActividadIdAndEstudianteId(actividadId, estudianteId).map(mapper::toDomain);
    }
}