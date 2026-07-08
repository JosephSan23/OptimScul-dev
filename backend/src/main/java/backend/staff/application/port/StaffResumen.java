package backend.staff.application.port;

import java.time.LocalDateTime;
import java.util.UUID;

public interface StaffResumen {
    UUID getUsuarioId();
    String getUsername();
    String getEmailLogin();
    String getEstado();
    String getPrimerNombre();
    String getPrimerApellido();
    String getNumeroDocumento();
    String getCorreo();
    String getRolCodigo();
    String getRolNombre();
    LocalDateTime getUltimoLogin();
    LocalDateTime getCreatedAt();
}