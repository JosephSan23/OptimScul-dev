package backend.community.application;

import java.util.UUID;

public record AcudienteDetalle(
        UUID vinculoId, UUID acudienteId,
        String parentesco, Boolean esPrincipal, Boolean autorizadoRecogida, Boolean autorizadoInfoAcademica,
        String estado, String ocupacion, String empresa,
        String tipoDocumento, String numeroDocumento,
        String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
        String correo, String telefono) {
}