package backend.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Constraint de CLASE: valida que el número de documento sea coherente con el
 * tipo (ver {@link DocumentoIdentidad}). Se aplica sobre DTOs que implementen
 * {@link ConDocumento}. El error se reporta en el campo "numeroDocumento" con
 * el mensaje específico del tipo.
 */
@Documented
@Constraint(validatedBy = DocumentoValidoValidator.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface DocumentoValido {
    String message() default "Número de documento inválido para el tipo seleccionado.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
