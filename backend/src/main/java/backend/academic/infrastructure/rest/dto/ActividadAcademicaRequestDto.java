package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.EstadoActividad;
import backend.academic.domain.model.TipoActividad;

public class ActividadAcademicaRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private UUID cargaAcademicaId;

    private UUID periodoAcademicoId;

    private UUID sesionClaseId;

    @NotNull
    private TipoActividad tipo;

    @NotNull
    private String titulo;

    private String descripcion;

    @NotNull
    private LocalDateTime fechaPublicacion;

    private LocalDateTime fechaEntrega;

    private LocalDateTime fechaCierre;

    private BigDecimal porcentaje;

    private BigDecimal notaMaxima;

    @NotNull
    private Boolean permiteEntregaTardia;

    @NotNull
    private EstadoActividad estado;

    private UUID creadaPorUsuarioId;

    public ActividadAcademicaRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getCargaAcademicaId() { return cargaAcademicaId; }
    public void setCargaAcademicaId(UUID cargaAcademicaId) { this.cargaAcademicaId = cargaAcademicaId; }
    public UUID getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(UUID periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }
    public UUID getSesionClaseId() { return sesionClaseId; }
    public void setSesionClaseId(UUID sesionClaseId) { this.sesionClaseId = sesionClaseId; }
    public TipoActividad getTipo() { return tipo; }
    public void setTipo(TipoActividad tipo) { this.tipo = tipo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }
    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }
    public BigDecimal getNotaMaxima() { return notaMaxima; }
    public void setNotaMaxima(BigDecimal notaMaxima) { this.notaMaxima = notaMaxima; }
    public Boolean getPermiteEntregaTardia() { return permiteEntregaTardia; }
    public void setPermiteEntregaTardia(Boolean permiteEntregaTardia) { this.permiteEntregaTardia = permiteEntregaTardia; }
    public EstadoActividad getEstado() { return estado; }
    public void setEstado(EstadoActividad estado) { this.estado = estado; }
    public UUID getCreadaPorUsuarioId() { return creadaPorUsuarioId; }
    public void setCreadaPorUsuarioId(UUID creadaPorUsuarioId) { this.creadaPorUsuarioId = creadaPorUsuarioId; }
}
