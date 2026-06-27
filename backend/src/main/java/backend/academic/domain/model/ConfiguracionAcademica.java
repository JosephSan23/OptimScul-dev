package backend.academic.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ConfiguracionAcademica {

    private UUID id;
    private UUID institucionId;
    private Boolean usaPeriodos;
    private Short numeroPeriodos;
    private BigDecimal notaMinimaAprobacion;
    private BigDecimal notaMinima;
    private BigDecimal notaMaxima;
    private Short decimalesNota;
    private Boolean usaRecuperacion;
    private Boolean asistenciaPorClase;
    private Boolean manejaComportamiento;
    private Boolean manejaPuestos;
    private BigDecimal porcentajeInasistenciaReprobacion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ConfiguracionAcademica() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public Boolean getUsaPeriodos() { return usaPeriodos; }
    public void setUsaPeriodos(Boolean usaPeriodos) { this.usaPeriodos = usaPeriodos; }
    public Short getNumeroPeriodos() { return numeroPeriodos; }
    public void setNumeroPeriodos(Short numeroPeriodos) { this.numeroPeriodos = numeroPeriodos; }
    public BigDecimal getNotaMinimaAprobacion() { return notaMinimaAprobacion; }
    public void setNotaMinimaAprobacion(BigDecimal notaMinimaAprobacion) { this.notaMinimaAprobacion = notaMinimaAprobacion; }
    public BigDecimal getNotaMinima() { return notaMinima; }
    public void setNotaMinima(BigDecimal notaMinima) { this.notaMinima = notaMinima; }
    public BigDecimal getNotaMaxima() { return notaMaxima; }
    public void setNotaMaxima(BigDecimal notaMaxima) { this.notaMaxima = notaMaxima; }
    public Short getDecimalesNota() { return decimalesNota; }
    public void setDecimalesNota(Short decimalesNota) { this.decimalesNota = decimalesNota; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
