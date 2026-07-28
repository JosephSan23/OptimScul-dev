package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.EstadoEntregaActividad;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "entrega_actividad", schema = "optimscul")
public class EntregaActividadEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "actividad_id")
    private UUID actividadId;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "comentario_estudiante")
    private String comentarioEstudiante;

    @Column(name = "archivo_url")
    private String archivoUrl;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado", columnDefinition = "estado_entrega_actividad_enum")
    private EstadoEntregaActividad estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
