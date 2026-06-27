package backend.finance.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.finance.domain.model.TipoBeneficioFinanciero;

@Entity
@Table(name = "cuenta_cobro_beneficio", schema = "optimscul")
public class CuentaCobroBeneficioEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "cuenta_cobro_id")
    private UUID cuentaCobroId;

    @Column(name = "estudiante_beneficio_financiero_id")
    private UUID estudianteBeneficioFinancieroId;

    @Column(name = "beneficio_financiero_id")
    private UUID beneficioFinancieroId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoBeneficioFinanciero tipo;

    @Column(name = "porcentaje_aplicado")
    private BigDecimal porcentajeAplicado;

    @Column(name = "valor_fijo_aplicado")
    private BigDecimal valorFijoAplicado;

    @Column(name = "valor_descuento_aplicado")
    private BigDecimal valorDescuentoAplicado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CuentaCobroBeneficioEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getCuentaCobroId() { return cuentaCobroId; }
    public void setCuentaCobroId(UUID cuentaCobroId) { this.cuentaCobroId = cuentaCobroId; }
    public UUID getEstudianteBeneficioFinancieroId() { return estudianteBeneficioFinancieroId; }
    public void setEstudianteBeneficioFinancieroId(UUID estudianteBeneficioFinancieroId) { this.estudianteBeneficioFinancieroId = estudianteBeneficioFinancieroId; }
    public UUID getBeneficioFinancieroId() { return beneficioFinancieroId; }
    public void setBeneficioFinancieroId(UUID beneficioFinancieroId) { this.beneficioFinancieroId = beneficioFinancieroId; }
    public TipoBeneficioFinanciero getTipo() { return tipo; }
    public void setTipo(TipoBeneficioFinanciero tipo) { this.tipo = tipo; }
    public BigDecimal getPorcentajeAplicado() { return porcentajeAplicado; }
    public void setPorcentajeAplicado(BigDecimal porcentajeAplicado) { this.porcentajeAplicado = porcentajeAplicado; }
    public BigDecimal getValorFijoAplicado() { return valorFijoAplicado; }
    public void setValorFijoAplicado(BigDecimal valorFijoAplicado) { this.valorFijoAplicado = valorFijoAplicado; }
    public BigDecimal getValorDescuentoAplicado() { return valorDescuentoAplicado; }
    public void setValorDescuentoAplicado(BigDecimal valorDescuentoAplicado) { this.valorDescuentoAplicado = valorDescuentoAplicado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
