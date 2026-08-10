package backend.academic.infrastructure.rest.dto.CargaAcademica;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CargaAcademicaRequestDto {
    @NotNull(message = "Selecciona el año lectivo")
    private UUID anioLectivoId;
    @NotNull(message = "Selecciona el grupo")
    private UUID grupoId;
    @NotNull(message = "Selecciona la asignatura")
    private UUID asignaturaId;
    @NotNull(message = "Selecciona el profesor")
    private UUID profesorId;
    @Min(value = 1, message = "La intensidad horaria debe estar entre 1 y 40")
    @Max(value = 40, message = "La intensidad horaria debe estar entre 1 y 40")
    private Short intensidadHorariaSemanal;   // si viene null, se hereda de la asignatura
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observaciones;

    /** Si vienen ambas fechas, la de fin no puede ser anterior a la de inicio. */
    @AssertTrue(message = "La fecha de fin no puede ser anterior a la de inicio.")
    public boolean isRangoFechasValido() {
        if (fechaInicio == null || fechaFin == null) return true;
        return !fechaFin.isBefore(fechaInicio);
    }
}
