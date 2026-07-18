package backend.academic.infrastructure.rest.dto.CargaAcademica;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CargaAcademicaRequestDto {
    @NotNull private UUID anioLectivoId;
    @NotNull private UUID grupoId;
    @NotNull private UUID asignaturaId;
    @NotNull private UUID profesorId;
    private Short intensidadHorariaSemanal;   // si viene null, se hereda de la asignatura
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observaciones;
}