package backend.security.infrastructure.persistence.adapter;

import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.domain.model.UsuarioInstitucion;
import backend.security.infrastructure.persistence.UsuarioInstitucionJpaRepository;
import backend.security.infrastructure.persistence.mapper.UsuarioInstitucionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioInstitucionRepositoryAdapter implements UsuarioInstitucionRepository {

    private final UsuarioInstitucionJpaRepository jpaRepository;
    private final UsuarioInstitucionMapper mapper;

    public UsuarioInstitucionRepositoryAdapter(UsuarioInstitucionJpaRepository jpaRepository,
                                               UsuarioInstitucionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public UsuarioInstitucion save(UsuarioInstitucion entity) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(entity)));
    }

    @Override
    public Optional<UsuarioInstitucion> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<UsuarioInstitucion> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}