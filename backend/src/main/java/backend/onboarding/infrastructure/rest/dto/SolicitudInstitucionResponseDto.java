package backend.onboarding.infrastructure.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudInstitucionResponseDto {

    private UUID id;
    private String nombreColegio;
    private String nit;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String nombreContacto;
    private String correo;
    private String mensaje;
    private String estado;
    private UUID convertidaEnInstitucionId;
    private LocalDateTime createdAt;

}