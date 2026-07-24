package backend.academic.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActividadAcademica {

    private UUID id;
    private UUID institucionId;
    private UUID cargaAcademicaId;
    private UUID periodoAcademicoId;
    private UUID sesionClaseId;
    private TipoActividad tipo;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaCierre;
    private BigDecimal porcentaje;
    private BigDecimal notaMaxima;
    private Boolean permiteEntregaTardia;
    private EstadoActividad estado;
    private UUID creadaPorUsuarioId;
    private UUID updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
