package backend.finance.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;
import backend.finance.domain.model.TipoBeneficioFinanciero;

public class CuentaCobroBeneficioResponseDto {

    private UUID institucionId;

    private UUID cuentaCobroId;

    private UUID estudianteBeneficioFinancieroId;

    private UUID beneficioFinancieroId;

    private TipoBeneficioFinanciero tipo;

    private BigDecimal porcentajeAplicado;

    private BigDecimal valorFijoAplicado;

    private BigDecimal valorDescuentoAplicado;

    private String observaciones;

    public CuentaCobroBeneficioResponseDto() {}

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
}
