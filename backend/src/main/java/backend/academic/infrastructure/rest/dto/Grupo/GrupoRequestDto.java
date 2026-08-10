package backend.academic.infrastructure.rest.dto.Grupo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GrupoRequestDto {

    @NotNull(message = "Selecciona el año lectivo")
    private UUID anioLectivoId;
    private UUID sedeId; // opcional
    private UUID jornadaId; // opcional
    @NotBlank(message = "El código es obligatorio")
    private String codigo;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @Min(value = 1, message = "El cupo máximo debe estar entre 1 y 100")
    @Max(value = 100, message = "El cupo máximo debe estar entre 1 y 100")
    private Integer cupoMaximo; // opcional
    private String observaciones;

}
