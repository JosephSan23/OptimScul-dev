package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ConfiguracionAcademicaRequestDto {
    @NotNull private Boolean usaPeriodos;
    @NotNull private Short numeroPeriodos;
    @NotNull private BigDecimal notaMinimaAprobacion;
    @NotNull private BigDecimal notaMinima;
    @NotNull private BigDecimal notaMaxima;
    @NotNull private Short decimalesNota;
    @NotNull private Boolean usaRecuperacion;
    @NotNull private Boolean asistenciaPorClase;
    @NotNull private Boolean manejaComportamiento;
    @NotNull private Boolean manejaPuestos;
    private BigDecimal porcentajeInasistenciaReprobacion;
}