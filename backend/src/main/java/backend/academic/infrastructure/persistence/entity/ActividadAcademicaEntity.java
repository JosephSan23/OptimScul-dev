package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.EstadoActividad;
import backend.academic.domain.model.TipoActividad;

@Entity
@Table(name = "actividad_academica", schema = "optimscul")
public class ActividadAcademicaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "carga_academica_id")
    private UUID cargaAcademicaId;

    @Column(name = "periodo_academico_id")
    private UUID periodoAcademicoId;

    @Column(name = "sesion_clase_id")
    private UUID sesionClaseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoActividad tipo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "porcentaje")
    private BigDecimal porcentaje;

    @Column(name = "nota_maxima")
    private BigDecimal notaMaxima;

    @Column(name = "permite_entrega_tardia")
    private Boolean permiteEntregaTardia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoActividad estado;

    @Column(name = "creada_por_usuario_id")
    private UUID creadaPorUsuarioId;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ActividadAcademicaEntity() {}

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
