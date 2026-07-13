package backend.people.infrastructure.persistence.adapter;

import backend.people.application.port.JornadaRepository;
import backend.people.domain.model.Jornada;
import backend.people.infrastructure.persistence.JornadaJpaRepository;
import backend.people.infrastructure.persistence.mapper.JornadaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JornadaRepositoryAdapter implements JornadaRepository {

    private final JornadaJpaRepository jpaRepository;
    private final JornadaMapper mapper;

    public JornadaRepositoryAdapter(JornadaJpaRepository jpaRepository, JornadaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Jornada save(Jornada e) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<Jornada> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Jornada> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Jornada> findByInstitucionId(UUID institucionId) {
        return jpaRepository.findByInstitucionId(institucionId).stream().map(mapper::toDomain).toList();
    }
}