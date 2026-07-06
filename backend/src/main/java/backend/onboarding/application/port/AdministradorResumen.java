package backend.onboarding.application.port;

import java.time.LocalDateTime;
import java.util.UUID;

// Proyección de solo-lectura: la consulta la llena directo, sin mapper ni DTO aparte.
public interface AdministradorResumen {
    UUID getUsuarioId();
    String getUsername();
    String getEstado();
    Boolean getRequiereCambioPassword();
    String getPrimerNombre();
    String getPrimerApellido();
    String getNumeroDocumento();
    String getCorreo();
    UUID getInstitucionId();
    String getInstitucionNombre();
    LocalDateTime getUltimoLogin();
    LocalDateTime getCreatedAt();
}