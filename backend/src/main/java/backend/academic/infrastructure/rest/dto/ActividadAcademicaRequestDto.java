package backend.academic.infrastructure.rest.dto;

import backend.academic.domain.model.TipoActividad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ActividadAcademicaRequestDto {
    @NotNull private UUID periodoAcademicoId;
    @NotNull private TipoActividad tipo;
    @NotBlank private String titulo;
    private String descripcion;
    private LocalDate fechaEntrega;
    private LocalDate fechaCierre;
    private BigDecimal porcentaje;      // peso dentro del periodo (0–100)
    private BigDecimal notaMaxima;      // si null, hereda de la config
    private Boolean permiteEntregaTardia;
}