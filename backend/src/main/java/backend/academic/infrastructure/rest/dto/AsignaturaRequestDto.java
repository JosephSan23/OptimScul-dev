package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class AsignaturaRequestDto {

    @NotNull
    private UUID institucionId;

    private UUID areaId;

    @NotNull
    private String codigo;

    @NotNull
    private String nombre;

    private String descripcion;

    private Integer intensidadHorariaSemanal;

    @NotNull
    private Boolean requiereCalificacion;

    @NotNull
    private Boolean requiereRecuperacion;

    @NotNull
    private Boolean esComportamiento;

    @NotNull
    private Boolean activa;

    public AsignaturaRequestDto() {}

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
    public Integer getIntensidadHorariaSemanal() { return intensidadHorariaSemanal; }
    public void setIntensidadHorariaSemanal(Integer intensidadHorariaSemanal) { this.intensidadHorariaSemanal = intensidadHorariaSemanal; }
    public Boolean getRequiereCalificacion() { return requiereCalificacion; }
    public void setRequiereCalificacion(Boolean requiereCalificacion) { this.requiereCalificacion = requiereCalificacion; }
    public Boolean getRequiereRecuperacion() { return requiereRecuperacion; }
    public void setRequiereRecuperacion(Boolean requiereRecuperacion) { this.requiereRecuperacion = requiereRecuperacion; }
    public Boolean getEsComportamiento() { return esComportamiento; }
    public void setEsComportamiento(Boolean esComportamiento) { this.esComportamiento = esComportamiento; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}
