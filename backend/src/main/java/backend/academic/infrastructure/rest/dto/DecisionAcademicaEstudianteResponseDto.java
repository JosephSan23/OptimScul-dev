package backend.academic.infrastructure.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.ResultadoAcademico;

public class DecisionAcademicaEstudianteResponseDto {

    private UUID institucionId;

    private UUID anioLectivoId;

    private UUID estudianteId;

    private ResultadoAcademico resultado;

    private String observaciones;

    private LocalDateTime fechaDecision;

    private UUID usuarioId;

    public DecisionAcademicaEstudianteResponseDto() {}

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
}
