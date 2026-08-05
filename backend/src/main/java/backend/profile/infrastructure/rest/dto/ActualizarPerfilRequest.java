package backend.profile.infrastructure.rest.dto;

import java.time.LocalDate;

public record ActualizarPerfilRequest(
        String segundoNombre, String segundoApellido, LocalDate fechaNacimiento, String sexo, String nacionalidad,
        String telefono, String telefonoAlternativo, String correo,
        String direccion, String barrio, String ciudad, String departamento, String pais,
        String especialidad, String tituloProfesional) {
} // los dos últimos solo aplican a docentes