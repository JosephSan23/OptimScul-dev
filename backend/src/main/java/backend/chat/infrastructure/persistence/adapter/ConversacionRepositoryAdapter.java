package backend.chat.infrastructure.persistence.adapter;

import backend.chat.application.port.ConversacionRepository;
import backend.chat.domain.model.Conversacion;
import backend.chat.infrastructure.persistence.ConversacionJpaRepository;
import backend.chat.infrastructure.persistence.mapper.ConversacionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConversacionRepositoryAdapter implements ConversacionRepository {

    private final ConversacionJpaRepository jpa;
    private final ConversacionMapper mapper;

    public ConversacionRepositoryAdapter(ConversacionJpaRepository jpa, ConversacionMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Conversacion save(Conversacion c) {
        return mapper.toDomain(jpa.save(mapper.toEntity(c)));
    }

    @Override
    public Optional<Conversacion> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Conversacion> findByPar(UUID institucionId, UUID menor, UUID mayor) {
        return jpa.findByInstitucionIdAndUsuarioMenorIdAndUsuarioMayorId(institucionId, menor, mayor)
                .map(mapper::toDomain);
    }

    @Override
    public List<Conversacion> findAllDeUsuario(UUID usuarioId) {
        return jpa.findByUsuarioMenorIdOrUsuarioMayorId(usuarioId, usuarioId)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}