package backend.academic.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ResumenAnualEstudiante {

    private UUID id;
    private UUID institucionId;
    private UUID anioLectivoId;
    private UUID estudianteId;
    private UUID asignaturaId;
    private BigDecimal notaFinal;
    private Boolean aprueba;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResumenAnualEstudiante() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getAsignaturaId() { return asignaturaId; }
    public void setAsignaturaId(UUID asignaturaId) { this.asignaturaId = asignaturaId; }
    public BigDecimal getNotaFinal() { return notaFinal; }
    public void setNotaFinal(BigDecimal notaFinal) { this.notaFinal = notaFinal; }
    public Boolean getAprueba() { return aprueba; }
    public void setAprueba(Boolean aprueba) { this.aprueba = aprueba; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
