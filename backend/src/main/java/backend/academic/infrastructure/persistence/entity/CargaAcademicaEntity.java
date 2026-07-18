package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.EstadoCargaAcademica;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "carga_academica", schema = "optimscul")
public class CargaAcademicaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "anio_lectivo_id")
    private UUID anioLectivoId;

    @Column(name = "profesor_id")
    private UUID profesorId;

    @Column(name = "grupo_id")
    private UUID grupoId;

    @Column(name = "asignatura_id")
    private UUID asignaturaId;

    @Column(name = "intensidad_horaria_semanal")
    private Short intensidadHorariaSemanal;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado", columnDefinition = "estado_carga_academica_enum")
    private EstadoCargaAcademica estado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
