package backend.enrollment.infrastructure.rest.dto;

import backend.enrollment.domain.model.TipoMatricula;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MatriculaRequestDto {
    @NotNull(message = "Selecciona el estudiante")
    private UUID estudianteId;
    @NotNull(message = "Selecciona el año lectivo")
    private UUID anioLectivoId;
    @NotNull(message = "Selecciona el tipo de matrícula")
    private TipoMatricula tipo;
    private UUID grupoId;              // opcional: sin grupo queda en PREMATRICULA
    @PastOrPresent(message = "La fecha de matrícula no puede ser futura")
    private LocalDate fechaMatricula;  // opcional: default hoy
    private String observaciones;
}
