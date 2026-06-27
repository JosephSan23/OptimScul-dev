package backend.coexistence.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class TipoObservacion {

    private UUID id;
    private UUID institucionId;
    private String codigo;
    private String nombre;
    private String descripcion;
    private SeveridadObservacion severidad;
    private Boolean activa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TipoObservacion() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public SeveridadObservacion getSeveridad() { return severidad; }
    public void setSeveridad(SeveridadObservacion severidad) { this.severidad = severidad; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
