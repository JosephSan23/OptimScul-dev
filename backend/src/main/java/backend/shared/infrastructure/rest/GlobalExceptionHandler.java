package backend.shared.infrastructure.rest;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Manejador global de errores REST.
 *
 * Su función principal es traducir los fallos de Bean Validation (@Valid en
 * los DTOs) a la MISMA forma de respuesta que ya usan los controladores:
 * un JSON { "mensaje": "..." }. Así el frontend puede seguir leyendo
 * `err.error.mensaje` sin cambios y mostrar el motivo real al usuario.
 *
 * No intercepta SecurityException ni las RuntimeException de negocio: esas
 * las siguen manejando los try/catch de cada controlador (que devuelven
 * 403 / 404 / 409 según corresponde), por lo que el comportamiento actual
 * no cambia.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Respuesta uniforme de error, igual que los MensajeResponse de los controladores. */
    public record ErrorResponse(String mensaje) {
    }

    /** Falla la validación de un @RequestBody @Valid. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getDefaultMessage())
                .filter(m -> m != null && !m.isBlank())
                .distinct()
                .collect(Collectors.joining(" · "));
        if (mensaje.isBlank()) {
            mensaje = "Hay campos con datos inválidos. Revisa el formulario.";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(mensaje));
    }

    /** Falla la validación de parámetros sueltos (@RequestParam/@PathVariable con @Validated). */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> manejarConstraint(ConstraintViolationException ex) {
        String mensaje = ex.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .filter(m -> m != null && !m.isBlank())
                .distinct()
                .collect(Collectors.joining(" · "));
        if (mensaje.isBlank()) {
            mensaje = "Hay datos inválidos en la solicitud.";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(mensaje));
    }

    /** JSON mal formado o tipo de dato incompatible en el cuerpo. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("La solicitud tiene un formato inválido."));
    }
}
