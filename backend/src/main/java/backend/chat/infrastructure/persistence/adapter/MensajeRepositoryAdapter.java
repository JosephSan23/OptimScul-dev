package backend.chat.infrastructure.persistence.adapter;

import backend.chat.application.port.MensajeRepository;
import backend.chat.domain.model.Mensaje;
import backend.chat.infrastructure.persistence.MensajeJpaRepository;
import backend.chat.infrastructure.persistence.mapper.MensajeMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MensajeRepositoryAdapter implements MensajeRepository {

    private final MensajeJpaRepository jpa;
    private final MensajeMapper mapper;

    public MensajeRepositoryAdapter(MensajeJpaRepository jpa, MensajeMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Mensaje save(Mensaje m) {
        return mapper.toDomain(jpa.save(mapper.toEntity(m)));
    }

    @Override
    public List<Mensaje> findByConversacion(UUID conversacionId) {
        return jpa.findByConversacionIdOrderByCreatedAtAsc(conversacionId)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Mensaje> findUltimo(UUID conversacionId) {
        return jpa.findFirstByConversacionIdOrderByCreatedAtDesc(conversacionId).map(mapper::toDomain);
    }

    @Override
    public long contarNoLeidos(UUID conversacionId, UUID paraUsuarioId) {
        return jpa.countByConversacionIdAndRemitenteIdNotAndLeidoFalse(conversacionId, paraUsuarioId);
    }

    @Override
    public void marcarLeidos(UUID conversacionId, UUID paraUsuarioId) {
        jpa.marcarLeidos(conversacionId, paraUsuarioId);
    }

    @Override
    public List<Mensaje> findUltimosDeConversaciones(List<UUID> conversacionIds) {
        if (conversacionIds == null || conversacionIds.isEmpty()) return List.of();
        return jpa.findUltimosPorConversaciones(conversacionIds).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Map<UUID, Long> contarNoLeidosPorConversacion(List<UUID> conversacionIds, UUID paraUsuarioId) {
        if (conversacionIds == null || conversacionIds.isEmpty()) return Map.of();
        Map<UUID, Long> out = new HashMap<>();
        for (Object[] row : jpa.contarNoLeidosPorConversaciones(conversacionIds, paraUsuarioId)) {
            out.put((UUID) row[0], ((Number) row[1]).longValue());
        }
        return out;
    }
}
