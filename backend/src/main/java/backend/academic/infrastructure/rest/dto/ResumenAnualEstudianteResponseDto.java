package backend.academic.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ResumenAnualEstudianteResponseDto {

    private UUID institucionId;

    private UUID anioLectivoId;

    private UUID estudianteId;

    private UUID asignaturaId;

    private BigDecimal notaFinal;

    private Boolean aprueba;

    private String observaciones;

    public ResumenAnualEstudianteResponseDto() {}

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
}
