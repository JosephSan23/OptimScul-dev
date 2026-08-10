package backend.academic.infrastructure.rest.dto.AreaAcademica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AreaAcademicaRequestDto {

    @NotBlank(message = "El código es obligatorio")
    private String codigo;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;
    private String descripcion;

    public AreaAcademicaRequestDto() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
