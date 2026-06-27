package backend.people.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;
import backend.people.domain.model.EstadoRegistro;

public class JornadaRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private String codigo;

    @NotNull
    private String nombre;

    private String descripcion;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    @NotNull
    private EstadoRegistro estado;

    public JornadaRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public EstadoRegistro getEstado() { return estado; }
    public void setEstado(EstadoRegistro estado) { this.estado = estado; }
}
