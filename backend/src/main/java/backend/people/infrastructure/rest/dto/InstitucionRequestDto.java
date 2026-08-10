package backend.people.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import backend.people.domain.model.EstadoInstitucion;
import backend.people.domain.model.TipoInstitucion;

@Data
@NoArgsConstructor
public class InstitucionRequestDto {

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String nombreCorto;

    @NotNull(message = "Selecciona el tipo de institución")
    private TipoInstitucion tipoInstitucion;

    private String nit;

    private String dane;

    private String resolucionFuncionamiento;

    private String descripcion;

    @Email(message = "Escribe un correo válido (ej: nombre@dominio.com)")
    private String correoContacto;

    @Pattern(regexp = "^([0-9]{7}|[0-9]{10})?$",
             message = "Teléfono inválido (7 dígitos fijo o 10 dígitos celular)")
    private String telefonoContacto;

    private String sitioWeb;

    private String dominioCorreo;

    private String direccionPrincipal;

    private String ciudad;

    private String departamento;

    private String pais;

    private String logoUrl;

    private String zonaHoraria;

    private String moneda;

    private EstadoInstitucion estado;

}
