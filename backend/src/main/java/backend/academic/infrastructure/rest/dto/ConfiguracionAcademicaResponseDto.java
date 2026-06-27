package backend.academic.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ConfiguracionAcademicaResponseDto {

    private UUID institucionId;

    private Boolean usaPeriodos;

    private Integer numeroPeriodos;

    private BigDecimal notaMinimaAprobacion;

    private BigDecimal notaMinima;

    private BigDecimal notaMaxima;

    private Integer decimalesNota;

    private Boolean usaRecuperacion;

    private Boolean asistenciaPorClase;

    private Boolean manejaComportamiento;

    private Boolean manejaPuestos;

    private BigDecimal porcentajeInasistenciaReprobacion;

    public ConfiguracionAcademicaResponseDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public Boolean getUsaPeriodos() { return usaPeriodos; }
    public void setUsaPeriodos(Boolean usaPeriodos) { this.usaPeriodos = usaPeriodos; }
    public Integer getNumeroPeriodos() { return numeroPeriodos; }
    public void setNumeroPeriodos(Integer numeroPeriodos) { this.numeroPeriodos = numeroPeriodos; }
    public BigDecimal getNotaMinimaAprobacion() { return notaMinimaAprobacion; }
    public void setNotaMinimaAprobacion(BigDecimal notaMinimaAprobacion) { this.notaMinimaAprobacion = notaMinimaAprobacion; }
    public BigDecimal getNotaMinima() { return notaMinima; }
    public void setNotaMinima(BigDecimal notaMinima) { this.notaMinima = notaMinima; }
    public BigDecimal getNotaMaxima() { return notaMaxima; }
    public void setNotaMaxima(BigDecimal notaMaxima) { this.notaMaxima = notaMaxima; }
    public Integer getDecimalesNota() { return decimalesNota; }
    public void setDecimalesNota(Integer decimalesNota) { this.decimalesNota = decimalesNota; }
    public Boolean getUsaRecuperacion() { return usaRecuperacion; }
    public void setUsaRecuperacion(Boolean usaRecuperacion) { this.usaRecuperacion = usaRecuperacion; }
    public Boolean getAsistenciaPorClase() { return asistenciaPorClase; }
    public void setAsistenciaPorClase(Boolean asistenciaPorClase) { this.asistenciaPorClase = asistenciaPorClase; }
    public Boolean getManejaComportamiento() { return manejaComportamiento; }
    public void setManejaComportamiento(Boolean manejaComportamiento) { this.manejaComportamiento = manejaComportamiento; }
    public Boolean getManejaPuestos() { return manejaPuestos; }
    public void setManejaPuestos(Boolean manejaPuestos) { this.manejaPuestos = manejaPuestos; }
    public BigDecimal getPorcentajeInasistenciaReprobacion() { return porcentajeInasistenciaReprobacion; }
    public void setPorcentajeInasistenciaReprobacion(BigDecimal porcentajeInasistenciaReprobacion) { this.porcentajeInasistenciaReprobacion = porcentajeInasistenciaReprobacion; }
}
