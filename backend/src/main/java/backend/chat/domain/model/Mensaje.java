package backend.chat.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Mensaje {

    private UUID id;
    private UUID conversacionId;
    private UUID remitenteId;
    private String contenido;
    private boolean leido;
    private LocalDateTime createdAt;

    public Mensaje() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getConversacionId() { return conversacionId; }
    public void setConversacionId(UUID conversacionId) { this.conversacionId = conversacionId; }
    public UUID getRemitenteId() { return remitenteId; }
    public void setRemitenteId(UUID remitenteId) { this.remitenteId = remitenteId; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public boolean isLeido() { return leido; }
    public void setLeido(boolean leido) { this.leido = leido; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}