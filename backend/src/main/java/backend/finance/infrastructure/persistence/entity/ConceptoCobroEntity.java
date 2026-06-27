package backend.finance.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.finance.domain.model.CategoriaConceptoCobro;

@Entity
@Table(name = "concepto_cobro", schema = "optimscul")
public class ConceptoCobroEntity {

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
    @Column(name = "categoria")
    private CategoriaConceptoCobro categoria;

    @Column(name = "valor_base")
    private BigDecimal valorBase;

    @Column(name = "es_recurrente")
    private Boolean esRecurrente;

    @Column(name = "requiere_vencimiento")
    private Boolean requiereVencimiento;

    @Column(name = "permite_descuento")
    private Boolean permiteDescuento;

    @Column(name = "permite_recargo")
    private Boolean permiteRecargo;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ConceptoCobroEntity() {}

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
    public CategoriaConceptoCobro getCategoria() { return categoria; }
    public void setCategoria(CategoriaConceptoCobro categoria) { this.categoria = categoria; }
    public BigDecimal getValorBase() { return valorBase; }
    public void setValorBase(BigDecimal valorBase) { this.valorBase = valorBase; }
    public Boolean getEsRecurrente() { return esRecurrente; }
    public void setEsRecurrente(Boolean esRecurrente) { this.esRecurrente = esRecurrente; }
    public Boolean getRequiereVencimiento() { return requiereVencimiento; }
    public void setRequiereVencimiento(Boolean requiereVencimiento) { this.requiereVencimiento = requiereVencimiento; }
    public Boolean getPermiteDescuento() { return permiteDescuento; }
    public void setPermiteDescuento(Boolean permiteDescuento) { this.permiteDescuento = permiteDescuento; }
    public Boolean getPermiteRecargo() { return permiteRecargo; }
    public void setPermiteRecargo(Boolean permiteRecargo) { this.permiteRecargo = permiteRecargo; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
