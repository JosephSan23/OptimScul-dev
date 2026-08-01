package backend.chat.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Conversacion {

    private UUID id;
    private UUID institucionId;
    private UUID usuarioMenorId;
    private UUID usuarioMayorId;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Conversacion() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getUsuarioMenorId() { return usuarioMenorId; }
    public void setUsuarioMenorId(UUID usuarioMenorId) { this.usuarioMenorId = usuarioMenorId; }
    public UUID getUsuarioMayorId() { return usuarioMayorId; }
    public void setUsuarioMayorId(UUID usuarioMayorId) { this.usuarioMayorId = usuarioMayorId; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    /** El otro participante distinto de {@code usuarioId}. */
    public UUID interlocutorDe(UUID usuarioId) {
        return usuarioId.equals(usuarioMenorId) ? usuarioMayorId : usuarioMenorId;
    }

    public boolean participa(UUID usuarioId) {
        return usuarioId.equals(usuarioMenorId) || usuarioId.equals(usuarioMayorId);
    }
}