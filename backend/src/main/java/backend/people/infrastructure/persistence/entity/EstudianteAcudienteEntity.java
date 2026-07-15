package backend.people.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.people.domain.model.TipoParentesco;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estudiante_acudiente", schema = "optimscul")
public class EstudianteAcudienteEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Column(name = "acudiente_id")
    private UUID acudienteId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "parentesco", columnDefinition = "parentesco_tipo_enum")
    private TipoParentesco parentesco;

    @Column(name = "parentesco_id")
    private UUID parentescoId;

    @Column(name = "es_principal")
    private Boolean esPrincipal;

    @Column(name = "autorizado_recogida")
    private Boolean autorizadoRecogida;

    @Column(name = "autorizado_info_academica")
    private Boolean autorizadoInfoAcademica;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
