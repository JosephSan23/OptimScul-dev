package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;
import backend.academic.domain.model.DiaSemana;

public class HorarioCargaRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private UUID cargaAcademicaId;

    private UUID sedeId;

    @NotNull
    private DiaSemana diaSemana;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    private String aula;

    @NotNull
    private Boolean activo;

    public HorarioCargaRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getCargaAcademicaId() { return cargaAcademicaId; }
    public void setCargaAcademicaId(UUID cargaAcademicaId) { this.cargaAcademicaId = cargaAcademicaId; }
    public UUID getSedeId() { return sedeId; }
    public void setSedeId(UUID sedeId) { this.sedeId = sedeId; }
    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
