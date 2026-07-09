package backend.staff.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EditarStaffRequestDto {
    @NotBlank private String rolCodigo;
    @NotBlank private String tipoDocumento;
    @NotBlank private String numeroDocumento;
    @NotBlank private String primerNombre;
    private String segundoNombre;
    @NotBlank private String primerApellido;
    private String segundoApellido;
    @NotBlank @Email private String correo;
    private String telefono;
    private String telefonoAlternativo;
    private LocalDate fechaNacimiento;    // formato "1990-05-20"
    private String sexo;                  // valor válido del enum Sexo
    private String nacionalidad;
    private String direccion;
    private String barrio;
    private String ciudad;
    private String departamento;
    private String pais;
    private String observaciones;
}