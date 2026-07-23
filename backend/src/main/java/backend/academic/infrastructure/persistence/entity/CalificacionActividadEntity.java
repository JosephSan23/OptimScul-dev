package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "calificacion_actividad", schema = "optimscul")
public class CalificacionActividadEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "actividad_id")
    private UUID actividadId;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Column(name = "entrega_actividad_id")
    private UUID entregaActividadId;

    @Column(name = "nota_obtenida")
    private BigDecimal notaObtenida;

    @Column(name = "observacion_docente")
    private String observacionDocente;

    @Column(name = "calificada_por_usuario_id")
    private UUID calificadaPorUsuarioId;

    @Column(name = "fecha_calificacion")
    private LocalDateTime fechaCalificacion;

    @Column(name = "es_recuperacion")
    private Boolean esRecuperacion;

    @Column(name = "anulada")
    private Boolean anulada;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
