package backend.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implementa {@link DocumentoValido}. Valida el número según el tipo y, si
 * falla, adjunta el mensaje específico del tipo al campo "numeroDocumento"
 * (así el GlobalExceptionHandler lo devuelve como { "mensaje": ... }).
 */
public class DocumentoValidoValidator
        implements ConstraintValidator<DocumentoValido, ConDocumento> {

    @Override
    public boolean isValid(ConDocumento dto, ConstraintValidatorContext ctx) {
        if (dto == null) return true;
        boolean ok = DocumentoIdentidad.esValido(dto.getTipoDocumento(), dto.getNumeroDocumento());
        if (!ok) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    DocumentoIdentidad.mensaje(dto.getTipoDocumento()))
                    .addPropertyNode("numeroDocumento")
                    .addConstraintViolation();
        }
        return ok;
    }
}
