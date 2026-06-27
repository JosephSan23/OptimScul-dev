package backend.security.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rol_permiso", schema = "optimscul")
public class RolPermisoEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "rol_id")
    private UUID rolId;

    @Column(name = "permiso_id")
    private UUID permisoId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public RolPermisoEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getRolId() { return rolId; }
    public void setRolId(UUID rolId) { this.rolId = rolId; }
    public UUID getPermisoId() { return permisoId; }
    public void setPermisoId(UUID permisoId) { this.permisoId = permisoId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
