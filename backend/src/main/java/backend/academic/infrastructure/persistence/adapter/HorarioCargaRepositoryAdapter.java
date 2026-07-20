package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.HorarioCargaRepository;
import backend.academic.domain.model.HorarioCarga;
import backend.academic.infrastructure.persistence.HorarioCargaJpaRepository;
import backend.academic.infrastructure.persistence.mapper.HorarioCargaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class HorarioCargaRepositoryAdapter implements HorarioCargaRepository {
    private final HorarioCargaJpaRepository jpa;
    private final HorarioCargaMapper mapper;

    public HorarioCargaRepositoryAdapter(HorarioCargaJpaRepository jpa, HorarioCargaMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public HorarioCarga save(HorarioCarga e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<HorarioCarga> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<HorarioCarga> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }
}