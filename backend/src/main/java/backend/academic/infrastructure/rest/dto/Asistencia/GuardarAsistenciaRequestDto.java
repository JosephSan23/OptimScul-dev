package backend.academic.infrastructure.rest.dto.Asistencia;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class GuardarAsistenciaRequestDto {
    @NotNull private LocalDate fecha;
    private String tema;
    @NotNull private List<Marca> marcas;

    @Data
    @NoArgsConstructor
    public static class Marca {
        @NotNull private UUID estudianteId;
        @NotNull private String tipo;      // PRESENTE | AUSENTE | TARDE | JUSTIFICADA
        private Short minutosTarde;
        private String observacion;
    }
}