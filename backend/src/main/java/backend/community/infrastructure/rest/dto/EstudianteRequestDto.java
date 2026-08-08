package backend.community.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Datos para CREAR un estudiante. Las reglas de formato replican en el
 * servidor lo que valida el frontend (core/validation), de modo que los
 * datos correctos quedan garantizados aunque alguien salte la UI.
 *
 * Nota: los patrones opcionales incluyen "^$|" para aceptar el valor vacío;
 * la obligatoriedad la impone @NotBlank por separado.
 */
@Data
@NoArgsConstructor
public class EstudianteRequestDto {

    @NotBlank(message = "Selecciona el tipo de documento")
    private String tipoDocumento;

    @NotBlank(message = "Ingresa el número de documento")
    @Pattern(regexp = "^$|^[0-9]{3,15}$",
             message = "Documento inválido (3 a 15 dígitos, solo números)")
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
    private String correo; // opcional

    private LocalDate fechaIngreso; // opcional; default hoy

    private String observaciones;
}
