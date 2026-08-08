package backend.community.infrastructure.rest.dto;

import backend.people.domain.model.EstadoEstudiante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Datos para EDITAR un estudiante (ficha completa). Mismas reglas de formato
 * que el frontend. Los campos opcionales aceptan vacío ("^$|") y solo validan
 * formato cuando traen contenido.
 */
@Data
@NoArgsConstructor
public class EditarEstudianteRequestDto {

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

    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñÜü' -]*$",
             message = "Segundo nombre inválido (solo texto)")
    private String segundoNombre;

    @NotBlank(message = "Ingresa el primer apellido")
    @Pattern(regexp = "^$|^[A-Za-zÁÉÍÓÚáéíóúÑñÜü' -]{2,}$",
             message = "Primer apellido inválido (mínimo 2 letras, solo texto)")
    private String primerApellido;

    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñÜü' -]*$",
             message = "Segundo apellido inválido (solo texto)")
    private String segundoApellido;

    @Email(message = "Escribe un correo válido (ej: nombre@dominio.com)")
    private String correo;

    @Pattern(regexp = "^([0-9]{7}|[0-9]{10})?$",
             message = "Teléfono inválido (7 dígitos fijo o 10 dígitos celular)")
    private String telefono;

    private LocalDate fechaNacimiento;
    private String direccion;
    private String ciudad;
    private LocalDate fechaIngreso;
    private EstadoEstudiante estado;
    private String observaciones;
}
