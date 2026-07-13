package backend.config.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class JornadaRequestDto {
    @NotBlank private String codigo;
    @NotBlank private String nombre;
    private String descripcion;
    private LocalTime horaInicio;   // "07:00"
    private LocalTime horaFin;      // "12:00"
}