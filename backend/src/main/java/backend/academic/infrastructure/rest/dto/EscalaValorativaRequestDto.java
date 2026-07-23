package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class EscalaValorativaRequestDto {
    @NotBlank private String nombre;
    private String abreviatura;
    @NotNull private BigDecimal notaMinima;
    @NotNull private BigDecimal notaMaxima;
    @NotNull private Boolean aprueba;
    @NotNull private Short orden;
}