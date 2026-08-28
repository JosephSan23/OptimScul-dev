package backend.academic.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeriodoAcademico {

    private UUID id;
    private UUID institucionId;
    private UUID anioLectivoId;
    private Short numero;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal peso;
    private EstadoPeriodo estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean boletinHabilitado = false;
}
