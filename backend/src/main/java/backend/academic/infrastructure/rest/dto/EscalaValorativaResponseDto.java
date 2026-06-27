package backend.academic.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class EscalaValorativaResponseDto {

    private UUID institucionId;

    private String nombre;

    private String abreviatura;

    private BigDecimal notaMinima;

    private BigDecimal notaMaxima;

    private Boolean aprueba;

    private Integer orden;

    private Boolean activa;

    public EscalaValorativaResponseDto() {}

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
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}
