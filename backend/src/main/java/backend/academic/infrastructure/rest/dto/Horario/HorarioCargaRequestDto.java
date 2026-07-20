package backend.academic.infrastructure.rest.dto.Horario;

import backend.academic.domain.model.DiaSemana;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class HorarioCargaRequestDto {
    @NotNull private UUID cargaAcademicaId;
    private UUID sedeId;                 // opcional
    @NotNull private DiaSemana diaSemana;
    @NotNull private LocalTime horaInicio;   // "07:00"
    @NotNull private LocalTime horaFin;      // "08:00"
    private String aula;                 // opcional
}