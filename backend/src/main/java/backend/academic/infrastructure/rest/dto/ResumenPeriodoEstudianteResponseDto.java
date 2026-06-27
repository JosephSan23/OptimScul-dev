package backend.academic.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ResumenPeriodoEstudianteResponseDto {

    private UUID institucionId;

    private UUID anioLectivoId;

    private UUID periodoAcademicoId;

    private UUID estudianteId;

    private UUID cargaAcademicaId;

    private BigDecimal notaFinal;

    private String observacion;

    private Boolean recuperacionAplica;

    private BigDecimal notaRecuperacion;

    private BigDecimal notaDefinitiva;

    public ResumenPeriodoEstudianteResponseDto() {}

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
}
