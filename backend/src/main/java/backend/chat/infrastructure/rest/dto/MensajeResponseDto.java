package backend.chat.infrastructure.rest.dto;

import backend.chat.domain.model.Mensaje;
import java.time.LocalDateTime;
import java.util.UUID;

public class MensajeResponseDto {
    private UUID id;
    private UUID conversacionId;
    private UUID remitenteId;
    private String contenido;
    private boolean leido;
    private LocalDateTime createdAt;

    public static MensajeResponseDto de(Mensaje m) {
        MensajeResponseDto d = new MensajeResponseDto();
        d.id = m.getId();
        d.conversacionId = m.getConversacionId();
        d.remitenteId = m.getRemitenteId();
        d.contenido = m.getContenido();
        d.leido = m.isLeido();
        d.createdAt = m.getCreatedAt();
        return d;
    }

    public UUID getId() { return id; }
    public UUID getConversacionId() { return conversacionId; }
    public UUID getRemitenteId() { return remitenteId; }
    public String getContenido() { return contenido; }
    public boolean isLeido() { return leido; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}