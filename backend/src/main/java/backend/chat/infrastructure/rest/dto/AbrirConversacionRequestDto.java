package backend.chat.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class AbrirConversacionRequestDto {
    @NotNull
    private UUID destinatarioId;
    public UUID getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(UUID destinatarioId) { this.destinatarioId = destinatarioId; }
}