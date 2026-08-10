package backend.config.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstitucionConfigRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String nombreCorto, descripcion, nit, dane, resolucionFuncionamiento;
    @Email(message = "Escribe un correo válido (ej: nombre@dominio.com)")
    private String correoContacto;
    @Pattern(regexp = "^([0-9]{7}|[0-9]{10})?$",
             message = "Teléfono inválido (7 dígitos fijo o 10 dígitos celular)")
    private String telefonoContacto;
    private String sitioWeb, direccionPrincipal,
            ciudad, departamento, pais, zonaHoraria, moneda;
}
