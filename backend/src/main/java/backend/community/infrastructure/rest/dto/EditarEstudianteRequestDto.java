package backend.community.infrastructure.rest.dto;

import backend.people.domain.model.EstadoEstudiante;
import backend.shared.validation.ConDocumento;
import backend.shared.validation.DocumentoValido;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Datos para EDITAR un estudiante (ficha completa). Mismas reglas de formato
 * que el frontend. Los campos opcionales aceptan vacío ("^$|") y solo validan
 * formato cuando traen contenido. El número de documento se valida según el
 * tipo mediante @DocumentoValido.
 */
@Data
@NoArgsConstructor
@DocumentoValido
public class EditarEstudianteRequestDto implements ConDocumento {

    @NotBlank(message = "Selecciona el tipo de documento")
    private String tipoDocumento;

    @NotBlank(message = "Ingresa el número de documento")
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

    /** No futura y con edad máxima de 100 años. Válida si está vacía. */
    @AssertTrue(message = "Fecha de nacimiento inválida (no puede ser futura ni de hace más de 100 años).")
    public boolean isFechaNacimientoValida() {
        if (fechaNacimiento == null) return true;
        LocalDate hoy = LocalDate.now();
        return !fechaNacimiento.isAfter(hoy) && !fechaNacimiento.isBefore(hoy.minusYears(100));
    }
}
