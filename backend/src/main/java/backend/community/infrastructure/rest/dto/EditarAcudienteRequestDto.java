package backend.community.infrastructure.rest.dto;

import backend.people.domain.model.EstadoAcudiente;
import backend.people.domain.model.TipoParentesco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditarAcudienteRequestDto {
    // persona
    @NotBlank private String tipoDocumento;
    @NotBlank private String numeroDocumento;
    @NotBlank private String primerNombre;
    private String segundoNombre;
    @NotBlank private String primerApellido;
    private String segundoApellido;
    @Email    private String correo;
    private String telefono;
    // acudiente
    private String ocupacion;
    private String empresa;
    private EstadoAcudiente estado;
    // vínculo
    @NotNull  private TipoParentesco parentesco;
    private Boolean esPrincipal;
    private Boolean autorizadoRecogida;
    private Boolean autorizadoInfoAcademica;
}