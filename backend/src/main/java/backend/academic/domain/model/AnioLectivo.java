package backend.academic.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AnioLectivo {

    private UUID id;
    private UUID institucionId;
    private Short anio;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoAnioLectivo estado;
    private Boolean esActual;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnioLectivo() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public Short getAnio() { return anio; }
    public void setAnio(Short anio) { this.anio = anio; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public EstadoAnioLectivo getEstado() { return estado; }
    public void setEstado(EstadoAnioLectivo estado) { this.estado = estado; }
    public Boolean getEsActual() { return esActual; }
    public void setEsActual(Boolean esActual) { this.esActual = esActual; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
