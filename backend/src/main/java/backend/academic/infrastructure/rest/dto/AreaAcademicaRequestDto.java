package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class AreaAcademicaRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private String codigo;

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private Boolean activa;

    public AreaAcademicaRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}
