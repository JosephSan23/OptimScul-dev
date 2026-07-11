package backend.config.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SedeRequestDto {
    @NotBlank private String codigo;
    @NotBlank private String nombre;
    private String descripcion;
    private String direccion;
    private String telefono;
    private String correo;
    private String ciudad;
    private String departamento;
    private String pais;
    private Boolean principal;
}