package backend.enrollment.infrastructure.rest.dto;

import backend.enrollment.domain.model.TipoMatricula;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MatriculaRequestDto {
    @NotNull private UUID estudianteId;
    @NotNull private UUID anioLectivoId;
    @NotNull private TipoMatricula tipo;
    private UUID grupoId;              // opcional: sin grupo queda en PREMATRICULA
    private LocalDate fechaMatricula;  // opcional: default hoy
    private String observaciones;
}