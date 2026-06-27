package backend.finance.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.finance.domain.model.ModoAplicacionBeneficio;
import backend.finance.domain.model.TipoBeneficioFinanciero;

@Entity
@Table(name = "beneficio_financiero", schema = "optimscul")
public class BeneficioFinancieroEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoBeneficioFinanciero tipo;

    @Column(name = "porcentaje")
    private BigDecimal porcentaje;

    @Column(name = "valor_fijo")
    private BigDecimal valorFijo;

    @Column(name = "concepto_cobro_id")
    private UUID conceptoCobroId;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "requiere_aprobacion")
    private Boolean requiereAprobacion;

    @Column(name = "acumulable")
    private Boolean acumulable;

    @Column(name = "prioridad")
    private Integer prioridad;

    @Enumerated(EnumType.STRING)
    @Column(name = "modo_aplicacion")
    private ModoAplicacionBeneficio modoAplicacion;

    @Column(name = "permite_convivir_con_mejor_beneficio")
    private Boolean permiteConvivirConMejorBeneficio;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public BeneficioFinancieroEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public TipoBeneficioFinanciero getTipo() { return tipo; }
    public void setTipo(TipoBeneficioFinanciero tipo) { this.tipo = tipo; }
    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }
    public BigDecimal getValorFijo() { return valorFijo; }
    public void setValorFijo(BigDecimal valorFijo) { this.valorFijo = valorFijo; }
    public UUID getConceptoCobroId() { return conceptoCobroId; }
    public void setConceptoCobroId(UUID conceptoCobroId) { this.conceptoCobroId = conceptoCobroId; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public Boolean getRequiereAprobacion() { return requiereAprobacion; }
    public void setRequiereAprobacion(Boolean requiereAprobacion) { this.requiereAprobacion = requiereAprobacion; }
    public Boolean getAcumulable() { return acumulable; }
    public void setAcumulable(Boolean acumulable) { this.acumulable = acumulable; }
    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer prioridad) { this.prioridad = prioridad; }
    public ModoAplicacionBeneficio getModoAplicacion() { return modoAplicacion; }
    public void setModoAplicacion(ModoAplicacionBeneficio modoAplicacion) { this.modoAplicacion = modoAplicacion; }
    public Boolean getPermiteConvivirConMejorBeneficio() { return permiteConvivirConMejorBeneficio; }
    public void setPermiteConvivirConMejorBeneficio(Boolean permiteConvivirConMejorBeneficio) { this.permiteConvivirConMejorBeneficio = permiteConvivirConMejorBeneficio; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
