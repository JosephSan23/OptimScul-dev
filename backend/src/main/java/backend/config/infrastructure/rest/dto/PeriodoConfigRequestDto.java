package backend.config.infrastructure.rest.dto;

import backend.academic.domain.model.EstadoPeriodo;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PeriodoConfigRequestDto {
    @NotNull(message = "El número es obligatorio")
    @Positive(message = "El número debe ser positivo")
    private Integer numero;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;
    private String descripcion;
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;
    @DecimalMin(value = "0", message = "El peso debe estar entre 0 y 100")
    @DecimalMax(value = "100", message = "El peso debe estar entre 0 y 100")
    private BigDecimal peso;          // opcional (pesos iguales → puede ir null)
    private EstadoPeriodo estado;     // opcional; si null → PLANEADO al crear

    /** La fecha de fin debe ser posterior a la de inicio. */
    @AssertTrue(message = "La fecha de fin no puede ser anterior a la de inicio.")
    public boolean isRangoFechasValido() {
        if (fechaInicio == null || fechaFin == null) return true;
        return fechaFin.isAfter(fechaInicio);
    }
}
