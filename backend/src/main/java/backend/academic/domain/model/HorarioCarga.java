package backend.academic.domain.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class HorarioCarga {

    private UUID id;
    private UUID institucionId;
    private UUID cargaAcademicaId;
    private UUID sedeId;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String aula;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public HorarioCarga() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
