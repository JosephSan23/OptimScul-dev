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
@Table(name = "configuracion_academica", schema = "optimscul")
public class ConfiguracionAcademicaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "usa_periodos")
    private Boolean usaPeriodos;

    @Column(name = "numero_periodos")
    private Short numeroPeriodos;

    @Column(name = "nota_minima_aprobacion")
    private BigDecimal notaMinimaAprobacion;

    @Column(name = "nota_minima")
    private BigDecimal notaMinima;

    @Column(name = "nota_maxima")
    private BigDecimal notaMaxima;

    @Column(name = "decimales_nota")
    private Short decimalesNota;

    @Column(name = "usa_recuperacion")
    private Boolean usaRecuperacion;

    @Column(name = "asistencia_por_clase")
    private Boolean asistenciaPorClase;

    @Column(name = "maneja_comportamiento")
    private Boolean manejaComportamiento;

    @Column(name = "maneja_puestos")
    private Boolean manejaPuestos;

    @Column(name = "porcentaje_inasistencia_reprobacion")
    private BigDecimal porcentajeInasistenciaReprobacion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
