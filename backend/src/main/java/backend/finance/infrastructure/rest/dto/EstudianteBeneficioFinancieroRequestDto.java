package backend.finance.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.finance.domain.model.EstadoBeneficioEstudiante;

public class EstudianteBeneficioFinancieroRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private UUID estudianteId;

    @NotNull
    private UUID beneficioFinancieroId;

    private UUID anioLectivoId;

    private UUID periodoAcademicoId;

    @NotNull
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @NotNull
    private EstadoBeneficioEstudiante estado;

    private String observaciones;

    private UUID aprobadoPorUsuarioId;

    private LocalDateTime fechaAprobacion;

    public EstudianteBeneficioFinancieroRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getBeneficioFinancieroId() { return beneficioFinancieroId; }
    public void setBeneficioFinancieroId(UUID beneficioFinancieroId) { this.beneficioFinancieroId = beneficioFinancieroId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(UUID periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public EstadoBeneficioEstudiante getEstado() { return estado; }
    public void setEstado(EstadoBeneficioEstudiante estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public UUID getAprobadoPorUsuarioId() { return aprobadoPorUsuarioId; }
    public void setAprobadoPorUsuarioId(UUID aprobadoPorUsuarioId) { this.aprobadoPorUsuarioId = aprobadoPorUsuarioId; }
    public LocalDateTime getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(LocalDateTime fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }
}
