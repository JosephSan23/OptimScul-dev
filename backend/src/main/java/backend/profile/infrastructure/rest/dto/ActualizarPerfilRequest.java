package backend.profile.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * Validación SOLO de formato (no de obligatoriedad): el backend maneja la
 * completitud del perfil por su cuenta (perfilCompleto / camposFaltantes), así
 * que aquí no forzamos @NotBlank para no romper el guardado progresivo.
 */
public record ActualizarPerfilRequest(
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñÜü' -]*$", message = "Segundo nombre inválido (solo texto)")
        String segundoNombre,
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñÜü' -]*$", message = "Segundo apellido inválido (solo texto)")
        String segundoApellido,
        @Past(message = "La fecha de nacimiento no puede ser futura")
        LocalDate fechaNacimiento,
        String sexo,
        String nacionalidad,
        @Pattern(regexp = "^([0-9]{7}|[0-9]{10})?$", message = "Teléfono inválido (7 dígitos fijo o 10 dígitos celular)")
        String telefono,
        @Pattern(regexp = "^([0-9]{7}|[0-9]{10})?$", message = "Teléfono alternativo inválido (7 dígitos fijo o 10 dígitos celular)")
        String telefonoAlternativo,
        @Email(message = "Escribe un correo válido (ej: nombre@dominio.com)")
        String correo,
        String direccion, String barrio, String ciudad, String departamento, String pais,
        String especialidad, String tituloProfesional) {
} // los dos últimos solo aplican a docentes
