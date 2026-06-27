package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import backend.academic.domain.model.EstadoAnioLectivo;

public class AnioLectivoRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private Integer anio;

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;

    @NotNull
    private EstadoAnioLectivo estado;

    @NotNull
    private Boolean esActual;

    public AnioLectivoRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
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
}
