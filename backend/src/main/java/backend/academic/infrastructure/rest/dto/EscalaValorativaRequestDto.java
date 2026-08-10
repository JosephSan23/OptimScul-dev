package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class EscalaValorativaRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String abreviatura;
    @NotNull(message = "La nota mínima es obligatoria")
    @PositiveOrZero(message = "La nota mínima no puede ser negativa")
    private BigDecimal notaMinima;
    @NotNull(message = "La nota máxima es obligatoria")
    @PositiveOrZero(message = "La nota máxima no puede ser negativa")
    private BigDecimal notaMaxima;
    @NotNull(message = "Indica si aprueba")
    private Boolean aprueba;
    @NotNull(message = "El orden es obligatorio")
    private Short orden;

    /** La nota máxima debe ser mayor que la mínima. */
    @AssertTrue(message = "La nota máxima debe ser mayor que la nota mínima.")
    public boolean isRangoValido() {
        if (notaMinima == null || notaMaxima == null) return true;
        return notaMaxima.compareTo(notaMinima) > 0;
    }
}
