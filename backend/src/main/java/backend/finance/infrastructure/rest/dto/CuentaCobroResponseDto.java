package backend.finance.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.finance.domain.model.EstadoCuentaCobro;

public class CuentaCobroResponseDto {

    private UUID institucionId;

    private UUID estudianteId;

    private UUID conceptoCobroId;

    private UUID anioLectivoId;

    private UUID periodoAcademicoId;

    private String codigo;

    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

    private String descripcion;

    private BigDecimal valorBase;

    private BigDecimal descuento;

    private BigDecimal recargo;

    private BigDecimal total;

    private BigDecimal saldo;

    private EstadoCuentaCobro estado;

    private String observaciones;

    private UUID anuladoPorUsuarioId;

    private LocalDateTime fechaAnulacion;

    public CuentaCobroResponseDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getConceptoCobroId() { return conceptoCobroId; }
    public void setConceptoCobroId(UUID conceptoCobroId) { this.conceptoCobroId = conceptoCobroId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(UUID periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getValorBase() { return valorBase; }
    public void setValorBase(BigDecimal valorBase) { this.valorBase = valorBase; }
    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
    public BigDecimal getRecargo() { return recargo; }
    public void setRecargo(BigDecimal recargo) { this.recargo = recargo; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public EstadoCuentaCobro getEstado() { return estado; }
    public void setEstado(EstadoCuentaCobro estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public UUID getAnuladoPorUsuarioId() { return anuladoPorUsuarioId; }
    public void setAnuladoPorUsuarioId(UUID anuladoPorUsuarioId) { this.anuladoPorUsuarioId = anuladoPorUsuarioId; }
    public LocalDateTime getFechaAnulacion() { return fechaAnulacion; }
    public void setFechaAnulacion(LocalDateTime fechaAnulacion) { this.fechaAnulacion = fechaAnulacion; }
}
