package backend.community.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EstudianteRequestDto {
    @NotBlank
    private String tipoDocumento;
    @NotBlank
    private String numeroDocumento;
    @NotBlank
    private String primerNombre;
    @NotBlank
    private String primerApellido;
    @Email
    private String correo; // opcional
    private LocalDate fechaIngreso; // opcional; default hoy
    private String observaciones;
}