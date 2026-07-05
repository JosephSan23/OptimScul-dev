package backend.security.infrastructure.persistence.adapter;

import backend.security.application.port.RolRepository;
import backend.security.domain.model.Rol;
import backend.security.infrastructure.persistence.RolJpaRepository;
import backend.security.infrastructure.persistence.mapper.RolMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RolRepositoryAdapter implements RolRepository {

    private final RolJpaRepository jpaRepository;
    private final RolMapper mapper;

    public RolRepositoryAdapter(RolJpaRepository jpaRepository, RolMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Rol save(Rol entity) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(entity)));
    }

    @Override
    public Optional<Rol> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Rol> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Rol> findByCodigo(String codigo) {
        return jpaRepository.findByCodigo(codigo).map(mapper::toDomain);
    }
}