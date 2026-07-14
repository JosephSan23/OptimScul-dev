package backend.config.infrastructure.rest.dto;

import backend.academic.domain.model.EstadoPeriodo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PeriodoConfigRequestDto {
    @NotNull  private Integer numero;
    @NotBlank private String nombre;
    private String descripcion;
    @NotNull  private LocalDate fechaInicio;
    @NotNull  private LocalDate fechaFin;
    private BigDecimal peso;          // opcional (pesos iguales → puede ir null)
    private EstadoPeriodo estado;     // opcional; si null → PLANEADO al crear
}