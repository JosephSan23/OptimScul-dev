package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.CalificacionActividadRepository;
import backend.academic.domain.model.CalificacionActividad;
import backend.academic.infrastructure.persistence.CalificacionActividadJpaRepository;
import backend.academic.infrastructure.persistence.mapper.CalificacionActividadMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CalificacionActividadRepositoryAdapter implements CalificacionActividadRepository {
    private final CalificacionActividadJpaRepository jpa;
    private final CalificacionActividadMapper mapper;

    public CalificacionActividadRepositoryAdapter(CalificacionActividadJpaRepository jpa,
            CalificacionActividadMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public CalificacionActividad save(CalificacionActividad e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<CalificacionActividad> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<CalificacionActividad> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<CalificacionActividad> findByActividadId(UUID actividadId) {
        return jpa.findByActividadId(actividadId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<CalificacionActividad> findByActividadIdAndEstudianteId(UUID actividadId, UUID estudianteId) {
        return jpa.findByActividadIdAndEstudianteId(actividadId, estudianteId).map(mapper::toDomain);
    }

    @Override
    public boolean existsByActividadId(UUID actividadId) {
        return jpa.existsByActividadId(actividadId);
    }
}