package backend.config.infrastructure.rest.dto;

import backend.academic.domain.model.EstadoAnioLectivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AnioLectivoConfigRequestDto {
    @NotNull  private Integer anio;
    @NotBlank private String nombre;
    private String descripcion;
    @NotNull  private LocalDate fechaInicio;
    @NotNull  private LocalDate fechaFin;
    private EstadoAnioLectivo estado;   // opcional; si null → PLANEACION al crear
}