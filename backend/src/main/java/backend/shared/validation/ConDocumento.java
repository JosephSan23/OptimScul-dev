package backend.shared.validation;

/**
 * Contrato que expone el tipo y el número de documento de un DTO,
 * para que el constraint de clase {@link DocumentoValido} pueda
 * validar el número según el tipo sin acoplarse a un DTO concreto.
 *
 * Lombok (@Data) genera estos getters automáticamente en los DTOs.
 */
public interface ConDocumento {
    String getTipoDocumento();
    String getNumeroDocumento();
}
