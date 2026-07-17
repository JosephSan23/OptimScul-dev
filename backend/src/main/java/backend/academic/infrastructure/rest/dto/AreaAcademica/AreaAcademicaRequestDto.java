package backend.academic.infrastructure.rest.dto.AreaAcademica;

import jakarta.validation.constraints.NotBlank;

public class AreaAcademicaRequestDto {

    @NotBlank private String codigo;
    @NotBlank private String nombre;
    private String descripcion;

    public AreaAcademicaRequestDto() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}