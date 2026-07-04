package backend.people.infrastructure.persistence.adapter;

import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.Institucion;
import backend.people.infrastructure.persistence.InstitucionJpaRepository;
import backend.people.infrastructure.persistence.entity.InstitucionEntity;
import backend.people.infrastructure.persistence.mapper.InstitucionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class InstitucionRepositoryAdapter implements InstitucionRepository {

    private final InstitucionJpaRepository jpaRepository;
    private final InstitucionMapper mapper;

    public InstitucionRepositoryAdapter(InstitucionJpaRepository jpaRepository, InstitucionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Institucion save(Institucion institucion) {
        InstitucionEntity guardada = jpaRepository.save(mapper.toEntity(institucion));
        return mapper.toDomain(guardada);
    }

    @Override
    public Optional<Institucion> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Institucion> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        return jpaRepository.existsByCodigo(codigo);
    }

}