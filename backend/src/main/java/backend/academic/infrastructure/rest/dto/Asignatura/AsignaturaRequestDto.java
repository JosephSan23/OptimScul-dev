package backend.academic.infrastructure.rest.dto.Asignatura;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class AsignaturaRequestDto {

    private UUID areaId;                    // opcional (la FK permite null)
    @NotBlank private String codigo;
    @NotBlank private String nombre;
    private String descripcion;
    private Short intensidadHorariaSemanal; // opcional
    private Boolean requiereCalificacion;   // default true
    private Boolean requiereRecuperacion;   // default true
    private Boolean esComportamiento;       // default false

    public AsignaturaRequestDto() {}

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
}