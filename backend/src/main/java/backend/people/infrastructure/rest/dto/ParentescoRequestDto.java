package backend.people.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
public class ParentescoRequestDto {

    @NotNull
    private String codigo;

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private Boolean activo;

    public ParentescoRequestDto() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
