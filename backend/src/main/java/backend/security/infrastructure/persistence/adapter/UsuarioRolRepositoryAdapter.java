package backend.security.infrastructure.persistence.adapter;

import backend.security.application.port.UsuarioRolRepository;
import backend.security.domain.model.UsuarioRol;
import backend.security.infrastructure.persistence.UsuarioRolJpaRepository;
import backend.security.infrastructure.persistence.mapper.UsuarioRolMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioRolRepositoryAdapter implements UsuarioRolRepository {

    private final UsuarioRolJpaRepository jpaRepository;
    private final UsuarioRolMapper mapper;

    public UsuarioRolRepositoryAdapter(UsuarioRolJpaRepository jpaRepository, UsuarioRolMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public UsuarioRol save(UsuarioRol entity) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(entity)));
    }

    @Override
    public Optional<UsuarioRol> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<UsuarioRol> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}