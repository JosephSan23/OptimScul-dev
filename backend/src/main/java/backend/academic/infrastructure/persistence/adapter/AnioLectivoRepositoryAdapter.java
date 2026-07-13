package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.academic.infrastructure.persistence.AnioLectivoJpaRepository;
import backend.academic.infrastructure.persistence.mapper.AnioLectivoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AnioLectivoRepositoryAdapter implements AnioLectivoRepository {
    private final AnioLectivoJpaRepository jpa;
    private final AnioLectivoMapper mapper;

    public AnioLectivoRepositoryAdapter(AnioLectivoJpaRepository jpa, AnioLectivoMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public AnioLectivo save(AnioLectivo e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<AnioLectivo> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<AnioLectivo> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<AnioLectivo> findByInstitucionId(UUID inst) {
        return jpa.findByInstitucionId(inst).stream().map(mapper::toDomain).toList();
    }
}