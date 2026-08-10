package backend.community.infrastructure.rest.dto;

import backend.people.domain.model.TipoParentesco;
import backend.shared.validation.ConDocumento;
import backend.shared.validation.DocumentoValido;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@DocumentoValido
public class AcudienteRequestDto implements ConDocumento {
    // persona
    @NotBlank(message = "Selecciona el tipo de documento")
    private String tipoDocumento;
    @NotBlank(message = "Ingresa el número de documento")
    private String numeroDocumento;
    @NotBlank(message = "Ingresa el primer nombre")
    @Pattern(regexp = "^$|^[A-Za-zÁÉÍÓÚáéíóúÑñÜü' -]{2,}$",
             message = "Primer nombre inválido (mínimo 2 letras, solo texto)")
    private String primerNombre;
    @NotBlank(message = "Ingresa el primer apellido")
    @Pattern(regexp = "^$|^[A-Za-zÁÉÍÓÚáéíóúÑñÜü' -]{2,}$",
             message = "Primer apellido inválido (mínimo 2 letras, solo texto)")
    private String primerApellido;
    @Email(message = "Escribe un correo válido (ej: nombre@dominio.com)")
    private String correo;
    // acudiente
    private String ocupacion;
    private String empresa;
    // vínculo con el estudiante
    @NotNull(message = "Selecciona el parentesco")
    private TipoParentesco parentesco;
    private Boolean esPrincipal;
    private Boolean autorizadoRecogida;
    private Boolean autorizadoInfoAcademica;
}
