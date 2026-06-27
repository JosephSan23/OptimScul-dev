package backend.finance.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;
import backend.finance.domain.model.CategoriaConceptoCobro;

public class ConceptoCobroResponseDto {

    private UUID institucionId;

    private String codigo;

    private String nombre;

    private String descripcion;

    private CategoriaConceptoCobro categoria;

    private BigDecimal valorBase;

    private Boolean esRecurrente;

    private Boolean requiereVencimiento;

    private Boolean permiteDescuento;

    private Boolean permiteRecargo;

    private Boolean activo;

    public ConceptoCobroResponseDto() {}

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
}
