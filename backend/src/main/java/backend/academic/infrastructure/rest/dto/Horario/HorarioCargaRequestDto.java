package backend.academic.infrastructure.rest.dto.Horario;

import backend.academic.domain.model.DiaSemana;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class HorarioCargaRequestDto {
    @NotNull(message = "Selecciona la asignación")
    private UUID cargaAcademicaId;
    private UUID sedeId;                 // opcional
    @NotNull(message = "Selecciona el día")
    private DiaSemana diaSemana;
    @NotNull(message = "Indica la hora de inicio")
    private LocalTime horaInicio;   // "07:00"
    @NotNull(message = "Indica la hora de fin")
    private LocalTime horaFin;      // "08:00"
    private String aula;                 // opcional

    /** La hora de fin debe ser posterior a la de inicio. */
    @AssertTrue(message = "La hora de fin debe ser posterior a la de inicio.")
    public boolean isRangoHorasValido() {
        if (horaInicio == null || horaFin == null) return true;
        return horaFin.isAfter(horaInicio);
    }
}
