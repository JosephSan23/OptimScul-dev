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
@Table(name = "escala_valorativa", schema = "optimscul")
public class EscalaValorativaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "abreviatura")
    private String abreviatura;

    @Column(name = "nota_minima")
    private BigDecimal notaMinima;

    @Column(name = "nota_maxima")
    private BigDecimal notaMaxima;

    @Column(name = "aprueba")
    private Boolean aprueba;

    @Column(name = "orden")
    private Short orden;

    @Column(name = "activa")
    private Boolean activa;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
