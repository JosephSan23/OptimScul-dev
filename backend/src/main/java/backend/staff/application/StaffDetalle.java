package backend.staff.application;

import java.time.LocalDate;
import java.util.UUID;

public record StaffDetalle(
        UUID usuarioId, String username, String emailLogin, String estado,
        String rolCodigo, String rolNombre,
        String tipoDocumento, String numeroDocumento,
        String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
        String correo, String telefono, String telefonoAlternativo,
        LocalDate fechaNacimiento, String sexo, String nacionalidad,
        String direccion, String barrio, String ciudad, String departamento, String pais,
        String fotoUrl, String observaciones) {}