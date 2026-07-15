package backend.community.infrastructure.rest.dto;

import backend.people.domain.model.TipoParentesco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcudienteRequestDto {
    // persona
    @NotBlank private String tipoDocumento;
    @NotBlank private String numeroDocumento;
    @NotBlank private String primerNombre;
    @NotBlank private String primerApellido;
    @Email    private String correo;
    // acudiente
    private String ocupacion;
    private String empresa;
    // vínculo con el estudiante
    @NotNull  private TipoParentesco parentesco;
    private Boolean esPrincipal;
    private Boolean autorizadoRecogida;
    private Boolean autorizadoInfoAcademica;
}