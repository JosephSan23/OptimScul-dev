package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.EstadoActividad;
import backend.academic.domain.model.TipoActividad;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "actividad_academica", schema = "optimscul")
public class ActividadAcademicaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "carga_academica_id")
    private UUID cargaAcademicaId;

    @Column(name = "periodo_academico_id")
    private UUID periodoAcademicoId;

    @Column(name = "sesion_clase_id")
    private UUID sesionClaseId;

    
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo", columnDefinition = "tipo_actividad_enum")
    private TipoActividad tipo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "porcentaje")
    private BigDecimal porcentaje;

    @Column(name = "nota_maxima")
    private BigDecimal notaMaxima;

    @Column(name = "permite_entrega_tardia")
    private Boolean permiteEntregaTardia;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado", columnDefinition = "estado_actividad_enum")
    private EstadoActividad estado;

    @Column(name = "creada_por_usuario_id")
    private UUID creadaPorUsuarioId;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
