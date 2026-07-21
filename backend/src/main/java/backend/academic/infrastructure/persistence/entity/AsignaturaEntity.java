package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asignatura", schema = "optimscul")
public class AsignaturaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "area_id")
    private UUID areaId;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "intensidad_horaria_semanal")
    private Short intensidadHorariaSemanal;

    @Column(name = "requiere_calificacion")
    private Boolean requiereCalificacion;

    @Column(name = "requiere_recuperacion")
    private Boolean requiereRecuperacion;

    @Column(name = "es_comportamiento")
    private Boolean esComportamiento;

    @Column(name = "activa")
    private Boolean activa;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
