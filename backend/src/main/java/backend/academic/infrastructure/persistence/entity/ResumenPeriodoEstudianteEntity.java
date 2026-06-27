package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "resumen_periodo_estudiante", schema = "optimscul")
public class ResumenPeriodoEstudianteEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "anio_lectivo_id")
    private UUID anioLectivoId;

    @Column(name = "periodo_academico_id")
    private UUID periodoAcademicoId;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Column(name = "carga_academica_id")
    private UUID cargaAcademicaId;

    @Column(name = "nota_final")
    private BigDecimal notaFinal;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "recuperacion_aplica")
    private Boolean recuperacionAplica;

    @Column(name = "nota_recuperacion")
    private BigDecimal notaRecuperacion;

    @Column(name = "nota_definitiva")
    private BigDecimal notaDefinitiva;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ResumenPeriodoEstudianteEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(UUID periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getCargaAcademicaId() { return cargaAcademicaId; }
    public void setCargaAcademicaId(UUID cargaAcademicaId) { this.cargaAcademicaId = cargaAcademicaId; }
    public BigDecimal getNotaFinal() { return notaFinal; }
    public void setNotaFinal(BigDecimal notaFinal) { this.notaFinal = notaFinal; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public Boolean getRecuperacionAplica() { return recuperacionAplica; }
    public void setRecuperacionAplica(Boolean recuperacionAplica) { this.recuperacionAplica = recuperacionAplica; }
    public BigDecimal getNotaRecuperacion() { return notaRecuperacion; }
    public void setNotaRecuperacion(BigDecimal notaRecuperacion) { this.notaRecuperacion = notaRecuperacion; }
    public BigDecimal getNotaDefinitiva() { return notaDefinitiva; }
    public void setNotaDefinitiva(BigDecimal notaDefinitiva) { this.notaDefinitiva = notaDefinitiva; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
