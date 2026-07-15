package backend.community.infrastructure.rest.dto;

import backend.people.domain.model.EstadoEstudiante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EditarEstudianteRequestDto {
    @NotBlank private String tipoDocumento;
    @NotBlank private String numeroDocumento;
    @NotBlank private String primerNombre;
    private String segundoNombre;
    @NotBlank private String primerApellido;
    private String segundoApellido;
    @Email    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String ciudad;
    private LocalDate fechaIngreso;
    private EstadoEstudiante estado;
    private String observaciones;
}