package backend.academic.application.usecase.actividad.calificacion;

import backend.academic.domain.model.ActividadAcademica;
import backend.academic.domain.model.ConfiguracionAcademica;
import backend.academic.domain.model.EstadoActividad;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class NotaFinalCalculator {

    public record Resultado(
            BigDecimal notaFinal,
            int actividadesCalificadas
    ) {
    }

    public Resultado calcular(
            List<ActividadAcademica> actividades,
            Map<UUID, Map<UUID, BigDecimal>> notas,
            UUID estudianteId,
            ConfiguracionAcademica cfg) {

        int decimales = cfg.getDecimalesNota() != null
                ? cfg.getDecimalesNota()
                : 2;

        BigDecimal escalaMax = cfg.getNotaMaxima();

        BigDecimal sumW = BigDecimal.ZERO;
        BigDecimal sumWF = BigDecimal.ZERO;
        BigDecimal sumFrac = BigDecimal.ZERO;

        int nFrac = 0;
        int calificadas = 0;

        for (ActividadAcademica actividad : actividades) {

            if (actividad.getEstado() == EstadoActividad.ANULADA)
                continue;

            BigDecimal nota = notas
                    .getOrDefault(actividad.getId(), Map.of())
                    .get(estudianteId);

            if (nota == null)
                continue;

            calificadas++;

            BigDecimal fraccion = nota.divide(
                    actividad.getNotaMaxima(),
                    6,
                    RoundingMode.HALF_UP
            );

            sumFrac = sumFrac.add(fraccion);
            nFrac++;

            BigDecimal peso = actividad.getPorcentaje();

            if (peso != null && peso.signum() > 0) {
                sumWF = sumWF.add(fraccion.multiply(peso));
                sumW = sumW.add(peso);
            }
        }

        BigDecimal notaFinal = null;

        if (sumW.signum() > 0) {
            notaFinal = sumWF
                    .divide(sumW, 6, RoundingMode.HALF_UP)
                    .multiply(escalaMax)
                    .setScale(decimales, RoundingMode.HALF_UP);

        } else if (nFrac > 0) {

            notaFinal = sumFrac
                    .divide(BigDecimal.valueOf(nFrac), 6, RoundingMode.HALF_UP)
                    .multiply(escalaMax)
                    .setScale(decimales, RoundingMode.HALF_UP);
        }

        return new Resultado(notaFinal, calificadas);
    }
}