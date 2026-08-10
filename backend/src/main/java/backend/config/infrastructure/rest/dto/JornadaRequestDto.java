package backend.config.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class JornadaRequestDto {
    @NotBlank(message = "El código es obligatorio")
    private String codigo;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;
    private String descripcion;
    // Las horas son opcionales y la jornada admite cruzar la medianoche,
    // por eso no se valida el orden entre ellas.
    private LocalTime horaInicio;   // "07:00"
    private LocalTime horaFin;      // "12:00"
}
