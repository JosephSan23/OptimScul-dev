package backend.academic.infrastructure.rest.dto.Horario;

import backend.academic.domain.model.HorarioCarga;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class HorarioCargaResponseDto {
    private UUID id;
    private UUID cargaAcademicaId, sedeId;
    private String diaSemana, horaInicio, horaFin, aula;
    private Boolean activo;

    public static HorarioCargaResponseDto desde(HorarioCarga h) {
        HorarioCargaResponseDto d = new HorarioCargaResponseDto();
        d.setId(h.getId());
        d.setCargaAcademicaId(h.getCargaAcademicaId());
        d.setSedeId(h.getSedeId());
        d.setDiaSemana(h.getDiaSemana() != null ? h.getDiaSemana().name() : null);
        d.setHoraInicio(h.getHoraInicio() != null ? h.getHoraInicio().toString() : null);
        d.setHoraFin(h.getHoraFin() != null ? h.getHoraFin().toString() : null);
        d.setAula(h.getAula());
        d.setActivo(h.getActivo());
        return d;
    }
}