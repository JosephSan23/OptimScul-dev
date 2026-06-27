package backend.security.infrastructure.rest.dto;

import java.util.UUID;

public class RolPermisoResponseDto {

    private UUID rolId;

    private UUID permisoId;

    public RolPermisoResponseDto() {}

    public UUID getRolId() { return rolId; }
    public void setRolId(UUID rolId) { this.rolId = rolId; }
    public UUID getPermisoId() { return permisoId; }
    public void setPermisoId(UUID permisoId) { this.permisoId = permisoId; }
}
