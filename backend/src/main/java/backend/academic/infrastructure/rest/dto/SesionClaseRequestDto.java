package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import backend.academic.domain.model.EstadoSesionClase;

public class SesionClaseRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private UUID cargaAcademicaId;

    private UUID horarioCargaId;

    @NotNull
    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private String tema;

    private String descripcion;

    @NotNull
    private EstadoSesionClase estado;

    @NotNull
    private Boolean fueReprogramada;

    public SesionClaseRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getCargaAcademicaId() { return cargaAcademicaId; }
    public void setCargaAcademicaId(UUID cargaAcademicaId) { this.cargaAcademicaId = cargaAcademicaId; }
    public UUID getHorarioCargaId() { return horarioCargaId; }
    public void setHorarioCargaId(UUID horarioCargaId) { this.horarioCargaId = horarioCargaId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public EstadoSesionClase getEstado() { return estado; }
    public void setEstado(EstadoSesionClase estado) { this.estado = estado; }
    public Boolean getFueReprogramada() { return fueReprogramada; }
    public void setFueReprogramada(Boolean fueReprogramada) { this.fueReprogramada = fueReprogramada; }
}
