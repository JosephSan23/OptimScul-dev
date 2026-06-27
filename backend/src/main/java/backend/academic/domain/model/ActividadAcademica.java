package backend.academic.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ActividadAcademica {

    private UUID id;
    private UUID institucionId;
    private UUID cargaAcademicaId;
    private UUID periodoAcademicoId;
    private UUID sesionClaseId;
    private TipoActividad tipo;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaCierre;
    private BigDecimal porcentaje;
    private BigDecimal notaMaxima;
    private Boolean permiteEntregaTardia;
    private EstadoActividad estado;
    private UUID creadaPorUsuarioId;
    private UUID updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ActividadAcademica() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
