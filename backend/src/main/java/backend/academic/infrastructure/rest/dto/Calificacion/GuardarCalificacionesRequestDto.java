package backend.academic.infrastructure.rest.dto.Calificacion;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class GuardarCalificacionesRequestDto {
    @NotNull private List<Nota> notas;

    @Data
    @NoArgsConstructor
    public static class Nota {
        @NotNull private UUID estudianteId;
        private BigDecimal notaObtenida;   // null = sin calificar todavía
        private String observacion;
        private Boolean esRecuperacion;
    }
}