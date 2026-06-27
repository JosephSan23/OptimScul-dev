package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import backend.academic.domain.model.EstadoPeriodo;

public class PeriodoAcademicoRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private UUID anioLectivoId;

    @NotNull
    private Integer numero;

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;

    private BigDecimal peso;

    @NotNull
    private EstadoPeriodo estado;

    public PeriodoAcademicoRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public BigDecimal getPeso() { return peso; }
    public void setPeso(BigDecimal peso) { this.peso = peso; }
    public EstadoPeriodo getEstado() { return estado; }
    public void setEstado(EstadoPeriodo estado) { this.estado = estado; }
}
