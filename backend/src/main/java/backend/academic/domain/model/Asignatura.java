package backend.academic.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Asignatura {

    private UUID id;
    private UUID institucionId;
    private UUID areaId;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Short intensidadHorariaSemanal;
    private Boolean requiereCalificacion;
    private Boolean requiereRecuperacion;
    private Boolean esComportamiento;
    private Boolean activa;
    private UUID createdBy;
    private UUID updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Asignatura() {}

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
