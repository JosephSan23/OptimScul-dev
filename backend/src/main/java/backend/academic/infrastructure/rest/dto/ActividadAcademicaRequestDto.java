package backend.academic.infrastructure.rest.dto;

import backend.academic.domain.model.TipoActividad;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ActividadAcademicaRequestDto {
    @NotNull(message = "Falta el periodo académico")
    private UUID periodoAcademicoId;
    @NotNull(message = "Selecciona el tipo de actividad")
    private TipoActividad tipo;
    @NotBlank(message = "Ingresa el título")
    @Size(min = 3, message = "El título debe tener al menos 3 caracteres")
    private String titulo;
    private String descripcion;
    private LocalDate fechaEntrega;
    private LocalDate fechaCierre;
    @DecimalMin(value = "0", message = "El peso debe estar entre 0 y 100")
    @DecimalMax(value = "100", message = "El peso debe estar entre 0 y 100")
    private BigDecimal porcentaje;      // peso dentro del periodo (0–100)
    @PositiveOrZero(message = "La nota máxima no puede ser negativa")
    private BigDecimal notaMaxima;      // si null, hereda de la config
    private Boolean permiteEntregaTardia;

    /** La fecha de cierre no puede ser anterior a la de entrega. */
    @AssertTrue(message = "La fecha de cierre no puede ser anterior a la de entrega.")
    public boolean isRangoFechasValido() {
        if (fechaEntrega == null || fechaCierre == null) return true;
        return !fechaCierre.isBefore(fechaEntrega);
    }
}
