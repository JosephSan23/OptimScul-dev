package backend.config.infrastructure.rest.dto;

import backend.academic.domain.model.EstadoAnioLectivo;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AnioLectivoConfigRequestDto {
    @NotNull(message = "El año es obligatorio")
    @Min(value = 2000, message = "El año debe estar entre 2000 y 2100")
    @Max(value = 2100, message = "El año debe estar entre 2000 y 2100")
    private Integer anio;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;
    private String descripcion;
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;
    private EstadoAnioLectivo estado;   // opcional; si null → PLANEACION al crear

    /** La fecha de fin debe ser posterior a la de inicio. */
    @AssertTrue(message = "La fecha de fin no puede ser anterior a la de inicio.")
    public boolean isRangoFechasValido() {
        if (fechaInicio == null || fechaFin == null) return true;
        return fechaFin.isAfter(fechaInicio);
    }
}
