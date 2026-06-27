package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "configuracion_academica", schema = "optimscul")
public class ConfiguracionAcademicaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "usa_periodos")
    private Boolean usaPeriodos;

    @Column(name = "numero_periodos")
    private Short numeroPeriodos;

    @Column(name = "nota_minima_aprobacion")
    private BigDecimal notaMinimaAprobacion;

    @Column(name = "nota_minima")
    private BigDecimal notaMinima;

    @Column(name = "nota_maxima")
    private BigDecimal notaMaxima;

    @Column(name = "decimales_nota")
    private Short decimalesNota;

    @Column(name = "usa_recuperacion")
    private Boolean usaRecuperacion;

    @Column(name = "asistencia_por_clase")
    private Boolean asistenciaPorClase;

    @Column(name = "maneja_comportamiento")
    private Boolean manejaComportamiento;

    @Column(name = "maneja_puestos")
    private Boolean manejaPuestos;

    @Column(name = "porcentaje_inasistencia_reprobacion")
    private BigDecimal porcentajeInasistenciaReprobacion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ConfiguracionAcademicaEntity() {}

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
