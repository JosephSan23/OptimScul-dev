package backend.profile.application;

import java.time.LocalDate;
import java.util.List;

public record PerfilVista(
        // identidad (solo lectura)
        String tipoDocumento, String numeroDocumento, String primerNombre, String primerApellido,
        String segundoNombre, String segundoApellido, LocalDate fechaNacimiento, String sexo, String nacionalidad,
        String telefono, String telefonoAlternativo, String correo,
        String direccion, String barrio, String ciudad, String departamento, String pais,
        String fotoUrl,
        boolean esDocente, String especialidad, String tituloProfesional,
        String username, boolean requiereCambioPassword, boolean perfilCompleto,
        List<String> camposFaltantes) {}