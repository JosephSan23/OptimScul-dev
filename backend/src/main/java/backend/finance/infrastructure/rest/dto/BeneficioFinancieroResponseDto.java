package backend.finance.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;
import backend.finance.domain.model.ModoAplicacionBeneficio;
import backend.finance.domain.model.TipoBeneficioFinanciero;

public class BeneficioFinancieroResponseDto {

    private UUID institucionId;

    private String codigo;

    private String nombre;

    private String descripcion;

    private TipoBeneficioFinanciero tipo;

    private BigDecimal porcentaje;

    private BigDecimal valorFijo;

    private UUID conceptoCobroId;

    private Boolean activo;

    private Boolean requiereAprobacion;

    private Boolean acumulable;

    private Integer prioridad;

    private ModoAplicacionBeneficio modoAplicacion;

    private Boolean permiteConvivirConMejorBeneficio;

    public BeneficioFinancieroResponseDto() {}

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
}
