package backend.security.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class RolPermisoRequestDto {

    @NotNull
    private UUID rolId;

    @NotNull
    private UUID permisoId;

    public RolPermisoRequestDto() {}

    public UUID getRolId() { return rolId; }
    public void setRolId(UUID rolId) { this.rolId = rolId; }
    public UUID getPermisoId() { return permisoId; }
    public void setPermisoId(UUID permisoId) { this.permisoId = permisoId; }
}
