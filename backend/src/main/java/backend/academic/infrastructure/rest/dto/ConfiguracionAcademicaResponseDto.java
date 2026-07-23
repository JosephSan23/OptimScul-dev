package backend.academic.infrastructure.rest.dto;

import backend.academic.domain.model.ConfiguracionAcademica;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ConfiguracionAcademicaResponseDto {
    private UUID id;
    private Boolean usaPeriodos;
    private Short numeroPeriodos;
    private BigDecimal notaMinimaAprobacion, notaMinima, notaMaxima;
    private Short decimalesNota;
    private Boolean usaRecuperacion, asistenciaPorClase, manejaComportamiento, manejaPuestos;
    private BigDecimal porcentajeInasistenciaReprobacion;

    public static ConfiguracionAcademicaResponseDto desde(ConfiguracionAcademica c) {
        ConfiguracionAcademicaResponseDto d = new ConfiguracionAcademicaResponseDto();
        d.setId(c.getId());
        d.setUsaPeriodos(c.getUsaPeriodos());
        d.setNumeroPeriodos(c.getNumeroPeriodos());
        d.setNotaMinimaAprobacion(c.getNotaMinimaAprobacion());
        d.setNotaMinima(c.getNotaMinima());
        d.setNotaMaxima(c.getNotaMaxima());
        d.setDecimalesNota(c.getDecimalesNota());
        d.setUsaRecuperacion(c.getUsaRecuperacion());
        d.setAsistenciaPorClase(c.getAsistenciaPorClase());
        d.setManejaComportamiento(c.getManejaComportamiento());
        d.setManejaPuestos(c.getManejaPuestos());
        d.setPorcentajeInasistenciaReprobacion(c.getPorcentajeInasistenciaReprobacion());
        return d;
    }
}