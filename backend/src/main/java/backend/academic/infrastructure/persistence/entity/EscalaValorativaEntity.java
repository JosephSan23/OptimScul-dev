package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    public EscalaValorativaEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getAbreviatura() { return abreviatura; }
    public void setAbreviatura(String abreviatura) { this.abreviatura = abreviatura; }
    public BigDecimal getNotaMinima() { return notaMinima; }
    public void setNotaMinima(BigDecimal notaMinima) { this.notaMinima = notaMinima; }
    public BigDecimal getNotaMaxima() { return notaMaxima; }
    public void setNotaMaxima(BigDecimal notaMaxima) { this.notaMaxima = notaMaxima; }
    public Boolean getAprueba() { return aprueba; }
    public void setAprueba(Boolean aprueba) { this.aprueba = aprueba; }
    public Short getOrden() { return orden; }
    public void setOrden(Short orden) { this.orden = orden; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
