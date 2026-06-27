package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "asignatura", schema = "optimscul")
public class AsignaturaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "area_id")
    private UUID areaId;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "intensidad_horaria_semanal")
    private Short intensidadHorariaSemanal;

    @Column(name = "requiere_calificacion")
    private Boolean requiereCalificacion;

    @Column(name = "requiere_recuperacion")
    private Boolean requiereRecuperacion;

    @Column(name = "es_comportamiento")
    private Boolean esComportamiento;

    @Column(name = "activa")
    private Boolean activa;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public AsignaturaEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getAreaId() { return areaId; }
    public void setAreaId(UUID areaId) { this.areaId = areaId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Short getIntensidadHorariaSemanal() { return intensidadHorariaSemanal; }
    public void setIntensidadHorariaSemanal(Short intensidadHorariaSemanal) { this.intensidadHorariaSemanal = intensidadHorariaSemanal; }
    public Boolean getRequiereCalificacion() { return requiereCalificacion; }
    public void setRequiereCalificacion(Boolean requiereCalificacion) { this.requiereCalificacion = requiereCalificacion; }
    public Boolean getRequiereRecuperacion() { return requiereRecuperacion; }
    public void setRequiereRecuperacion(Boolean requiereRecuperacion) { this.requiereRecuperacion = requiereRecuperacion; }
    public Boolean getEsComportamiento() { return esComportamiento; }
    public void setEsComportamiento(Boolean esComportamiento) { this.esComportamiento = esComportamiento; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
