package backend.academic.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class DecisionAcademicaEstudiante {

    private UUID id;
    private UUID institucionId;
    private UUID anioLectivoId;
    private UUID estudianteId;
    private ResultadoAcademico resultado;
    private String observaciones;
    private LocalDateTime fechaDecision;
    private UUID usuarioId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DecisionAcademicaEstudiante() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public ResultadoAcademico getResultado() { return resultado; }
    public void setResultado(ResultadoAcademico resultado) { this.resultado = resultado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public LocalDateTime getFechaDecision() { return fechaDecision; }
    public void setFechaDecision(LocalDateTime fechaDecision) { this.fechaDecision = fechaDecision; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
