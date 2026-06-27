package backend.security.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class RolPermiso {

    private UUID id;
    private UUID rolId;
    private UUID permisoId;
    private LocalDateTime createdAt;

    public RolPermiso() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getRolId() { return rolId; }
    public void setRolId(UUID rolId) { this.rolId = rolId; }
    public UUID getPermisoId() { return permisoId; }
    public void setPermisoId(UUID permisoId) { this.permisoId = permisoId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
