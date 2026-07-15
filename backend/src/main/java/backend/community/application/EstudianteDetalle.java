package backend.community.application;

import java.time.LocalDate;
import java.util.UUID;

public record EstudianteDetalle(
        UUID estudianteId, String codigoEstudiante, String estado, LocalDate fechaIngreso, String observaciones,
        String tipoDocumento, String numeroDocumento,
        String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
        String correo, String telefono, LocalDate fechaNacimiento, String direccion, String ciudad) {
}