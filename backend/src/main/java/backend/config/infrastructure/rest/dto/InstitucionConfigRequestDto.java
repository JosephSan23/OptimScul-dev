package backend.config.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstitucionConfigRequestDto {
    @NotBlank private String nombre;
    private String nombreCorto, descripcion, nit, dane, resolucionFuncionamiento,
            correoContacto, telefonoContacto, sitioWeb, direccionPrincipal,
            ciudad, departamento, pais, zonaHoraria, moneda;
}