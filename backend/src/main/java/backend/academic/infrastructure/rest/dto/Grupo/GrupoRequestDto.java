package backend.academic.infrastructure.rest.dto.Grupo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GrupoRequestDto {

    @NotNull
    private UUID anioLectivoId;
    private UUID sedeId; // opcional
    private UUID jornadaId; // opcional
    @NotBlank
    private String codigo;
    @NotBlank
    private String nombre;
    private Integer cupoMaximo; // opcional
    private String observaciones;

}