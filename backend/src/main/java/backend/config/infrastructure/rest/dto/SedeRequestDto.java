package backend.config.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SedeRequestDto {
    @NotBlank(message = "El código es obligatorio")
    private String codigo;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;
    private String descripcion;
    private String direccion;
    @Pattern(regexp = "^([0-9]{7}|[0-9]{10})?$",
             message = "Teléfono inválido (7 dígitos fijo o 10 dígitos celular)")
    private String telefono;
    @Email(message = "Escribe un correo válido (ej: nombre@dominio.com)")
    private String correo;
    private String ciudad;
    private String departamento;
    private String pais;
    private Boolean principal;
}
