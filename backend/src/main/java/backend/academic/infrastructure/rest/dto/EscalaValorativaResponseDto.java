package backend.academic.infrastructure.rest.dto;

import backend.academic.domain.model.EscalaValorativa;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EscalaValorativaResponseDto {
    private UUID id;
    private String nombre, abreviatura;
    private BigDecimal notaMinima, notaMaxima;
    private Boolean aprueba, activa;
    private Short orden;

    public static EscalaValorativaResponseDto desde(EscalaValorativa e) {
        EscalaValorativaResponseDto d = new EscalaValorativaResponseDto();
        d.setId(e.getId());
        d.setNombre(e.getNombre());
        d.setAbreviatura(e.getAbreviatura());
        d.setNotaMinima(e.getNotaMinima());
        d.setNotaMaxima(e.getNotaMaxima());
        d.setAprueba(e.getAprueba());
        d.setActiva(e.getActiva());
        d.setOrden(e.getOrden());
        return d;
    }
}