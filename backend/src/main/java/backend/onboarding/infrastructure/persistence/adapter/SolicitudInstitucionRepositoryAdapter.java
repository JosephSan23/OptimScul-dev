package backend.onboarding.infrastructure.persistence.adapter;

import backend.onboarding.application.port.SolicitudInstitucionRepository;
import backend.onboarding.domain.model.SolicitudInstitucion;
import backend.onboarding.infrastructure.persistence.SolicitudInstitucionJpaRepository;
import backend.onboarding.infrastructure.persistence.mapper.SolicitudInstitucionMapper;
import backend.onboarding.infrastructure.persistence.entity.SolicitudInstitucionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SolicitudInstitucionRepositoryAdapter implements SolicitudInstitucionRepository {

    private final SolicitudInstitucionJpaRepository jpaRepository;
    private final SolicitudInstitucionMapper mapper;

    public SolicitudInstitucionRepositoryAdapter(SolicitudInstitucionJpaRepository jpaRepository,
                                                 SolicitudInstitucionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public SolicitudInstitucion save(SolicitudInstitucion solicitud) {
        SolicitudInstitucionEntity guardada = jpaRepository.save(mapper.toEntity(solicitud));
        return mapper.toDomain(guardada);
    }

    @Override
    public Optional<SolicitudInstitucion> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<SolicitudInstitucion> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByEnviadaPorUsuarioIdAndEstado(UUID usuarioId, String estado) {
        return jpaRepository.existsByEnviadaPorUsuarioIdAndEstado(usuarioId, estado);
    }

    @Override
    public List<SolicitudInstitucion> findByEnviadaPorUsuarioId(UUID usuarioId) {
        return jpaRepository.findByEnviadaPorUsuarioId(usuarioId)
                .stream().map(mapper::toDomain).toList();
    }
}