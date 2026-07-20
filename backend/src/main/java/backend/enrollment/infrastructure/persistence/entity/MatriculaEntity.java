package backend.enrollment.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.enrollment.domain.model.EstadoMatricula;
import backend.enrollment.domain.model.TipoMatricula;

@Entity
@Table(name = "matricula", schema = "optimscul")
public class MatriculaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Column(name = "anio_lectivo_id")
    private UUID anioLectivoId;

    @Column(name = "grupo_id")
    private UUID grupoId;

    @Column(name = "codigo_matricula")
    private String codigoMatricula;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo", columnDefinition = "tipo_matricula_enum")
    private TipoMatricula tipo;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado", columnDefinition = "estado_matricula_enum")
    private EstadoMatricula estado;

    @Column(name = "fecha_matricula")
    private LocalDate fechaMatricula;

    @Column(name = "fecha_estado")
    private LocalDate fechaEstado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public MatriculaEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getGrupoId() { return grupoId; }
    public void setGrupoId(UUID grupoId) { this.grupoId = grupoId; }
    public String getCodigoMatricula() { return codigoMatricula; }
    public void setCodigoMatricula(String codigoMatricula) { this.codigoMatricula = codigoMatricula; }
    public TipoMatricula getTipo() { return tipo; }
    public void setTipo(TipoMatricula tipo) { this.tipo = tipo; }
    public EstadoMatricula getEstado() { return estado; }
    public void setEstado(EstadoMatricula estado) { this.estado = estado; }
    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDate fechaMatricula) { this.fechaMatricula = fechaMatricula; }
    public LocalDate getFechaEstado() { return fechaEstado; }
    public void setFechaEstado(LocalDate fechaEstado) { this.fechaEstado = fechaEstado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
