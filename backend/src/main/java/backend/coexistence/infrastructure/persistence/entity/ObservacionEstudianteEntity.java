package backend.coexistence.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.coexistence.domain.model.EstadoObservacion;
import backend.coexistence.domain.model.SeveridadObservacion;

@Entity
@Table(name = "observacion_estudiante", schema = "optimscul")
public class ObservacionEstudianteEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Column(name = "anio_lectivo_id")
    private UUID anioLectivoId;

    @Column(name = "periodo_academico_id")
    private UUID periodoAcademicoId;

    @Column(name = "grupo_id")
    private UUID grupoId;

    @Column(name = "carga_academica_id")
    private UUID cargaAcademicaId;

    @Column(name = "tipo_observacion_id")
    private UUID tipoObservacionId;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_evento")
    private LocalDate fechaEvento;

    @Enumerated(EnumType.STRING)
    @Column(name = "severidad")
    private SeveridadObservacion severidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoObservacion estado;

    @Column(name = "visible_acudiente")
    private Boolean visibleAcudiente;

    @Column(name = "requiere_seguimiento")
    private Boolean requiereSeguimiento;

    @Column(name = "cerrada_en")
    private LocalDateTime cerradaEn;

    @Column(name = "creada_por_usuario_id")
    private UUID creadaPorUsuarioId;

    @Column(name = "cerrada_por_usuario_id")
    private UUID cerradaPorUsuarioId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ObservacionEstudianteEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(UUID periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }
    public UUID getGrupoId() { return grupoId; }
    public void setGrupoId(UUID grupoId) { this.grupoId = grupoId; }
    public UUID getCargaAcademicaId() { return cargaAcademicaId; }
    public void setCargaAcademicaId(UUID cargaAcademicaId) { this.cargaAcademicaId = cargaAcademicaId; }
    public UUID getTipoObservacionId() { return tipoObservacionId; }
    public void setTipoObservacionId(UUID tipoObservacionId) { this.tipoObservacionId = tipoObservacionId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(LocalDate fechaEvento) { this.fechaEvento = fechaEvento; }
    public SeveridadObservacion getSeveridad() { return severidad; }
    public void setSeveridad(SeveridadObservacion severidad) { this.severidad = severidad; }
    public EstadoObservacion getEstado() { return estado; }
    public void setEstado(EstadoObservacion estado) { this.estado = estado; }
    public Boolean getVisibleAcudiente() { return visibleAcudiente; }
    public void setVisibleAcudiente(Boolean visibleAcudiente) { this.visibleAcudiente = visibleAcudiente; }
    public Boolean getRequiereSeguimiento() { return requiereSeguimiento; }
    public void setRequiereSeguimiento(Boolean requiereSeguimiento) { this.requiereSeguimiento = requiereSeguimiento; }
    public LocalDateTime getCerradaEn() { return cerradaEn; }
    public void setCerradaEn(LocalDateTime cerradaEn) { this.cerradaEn = cerradaEn; }
    public UUID getCreadaPorUsuarioId() { return creadaPorUsuarioId; }
    public void setCreadaPorUsuarioId(UUID creadaPorUsuarioId) { this.creadaPorUsuarioId = creadaPorUsuarioId; }
    public UUID getCerradaPorUsuarioId() { return cerradaPorUsuarioId; }
    public void setCerradaPorUsuarioId(UUID cerradaPorUsuarioId) { this.cerradaPorUsuarioId = cerradaPorUsuarioId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
