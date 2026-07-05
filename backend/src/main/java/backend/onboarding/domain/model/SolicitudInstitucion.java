package backend.onboarding.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SolicitudInstitucion {

    private UUID id;

    // Datos que llegan del formulario
    private String nombreColegio;
    private String nit;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String nombreContacto;
    private String correo;
    private String mensaje;

    // Quién la envió (usuario logueado)
    private UUID enviadaPorUsuarioId;

    // Control del flujo de aprobación
    private String estado;                 // PENDIENTE, APROBADA, RECHAZADA
    private UUID revisadaPorUsuarioId;
    private LocalDateTime fechaRevision;
    private String motivoRechazo;

    // Enlace a la institución creada al aprobar
    private UUID convertidaEnInstitucionId;
    private LocalDateTime fechaConversion;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}