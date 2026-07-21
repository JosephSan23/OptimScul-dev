package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.AsistenciaClaseRepository;
import backend.academic.domain.model.AsistenciaClase;
import backend.academic.infrastructure.persistence.AsistenciaClaseJpaRepository;
import backend.academic.infrastructure.persistence.mapper.AsistenciaClaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AsistenciaClaseRepositoryAdapter implements AsistenciaClaseRepository {

    private final AsistenciaClaseJpaRepository jpa;
    private final AsistenciaClaseMapper mapper;

    public AsistenciaClaseRepositoryAdapter(AsistenciaClaseJpaRepository jpa, AsistenciaClaseMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public AsistenciaClase save(AsistenciaClase e) { return mapper.toDomain(jpa.save(mapper.toEntity(e))); }

    @Override
    public Optional<AsistenciaClase> findById(UUID id) { return jpa.findById(id).map(mapper::toDomain); }

    @Override
    public List<AsistenciaClase> findAll() { return jpa.findAll().stream().map(mapper::toDomain).toList(); }

    @Override
    public void deleteById(UUID id) { jpa.deleteById(id); }

    @Override
    public List<AsistenciaClase> findBySesionClaseId(UUID sesionClaseId) {
        return jpa.findBySesionClaseId(sesionClaseId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<AsistenciaClase> findBySesionClaseIdAndEstudianteId(UUID sesionClaseId, UUID estudianteId) {
        return jpa.findBySesionClaseIdAndEstudianteId(sesionClaseId, estudianteId).map(mapper::toDomain);
    }
}