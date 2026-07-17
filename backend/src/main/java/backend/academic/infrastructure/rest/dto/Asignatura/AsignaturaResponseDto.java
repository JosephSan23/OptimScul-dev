package backend.academic.infrastructure.rest.dto.Asignatura;

import backend.academic.domain.model.Asignatura;
import java.util.UUID;

public class AsignaturaResponseDto {

    private UUID id;
    private UUID areaId;
    private String areaNombre;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Short intensidadHorariaSemanal;
    private Boolean requiereCalificacion;
    private Boolean requiereRecuperacion;
    private Boolean esComportamiento;
    private Boolean activa;

    public AsignaturaResponseDto() {}

    public static AsignaturaResponseDto desde(Asignatura a) {
        AsignaturaResponseDto d = new AsignaturaResponseDto();
        d.setId(a.getId());
        d.setAreaId(a.getAreaId());
        d.setCodigo(a.getCodigo());
        d.setNombre(a.getNombre());
        d.setDescripcion(a.getDescripcion());
        d.setIntensidadHorariaSemanal(a.getIntensidadHorariaSemanal());
        d.setRequiereCalificacion(a.getRequiereCalificacion());
        d.setRequiereRecuperacion(a.getRequiereRecuperacion());
        d.setEsComportamiento(a.getEsComportamiento());
        d.setActiva(a.getActiva());
        return d;
    }

    public static AsignaturaResponseDto desde(Asignatura a, String areaNombre) {
        AsignaturaResponseDto d = desde(a);
        d.setAreaNombre(areaNombre);
        return d;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getAreaId() { return areaId; }
    public void setAreaId(UUID areaId) { this.areaId = areaId; }
    public String getAreaNombre() { return areaNombre; }
    public void setAreaNombre(String areaNombre) { this.areaNombre = areaNombre; }
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
}