package backend.academic.infrastructure.rest.dto;

import java.time.LocalDate;
import java.util.UUID;
import backend.academic.domain.model.EstadoCargaAcademica;

public class CargaAcademicaResponseDto {

    private UUID institucionId;

    private UUID anioLectivoId;

    private UUID profesorId;

    private UUID grupoId;

    private UUID asignaturaId;

    private Integer intensidadHorariaSemanal;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private EstadoCargaAcademica estado;

    private String observaciones;

    public CargaAcademicaResponseDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getProfesorId() { return profesorId; }
    public void setProfesorId(UUID profesorId) { this.profesorId = profesorId; }
    public UUID getGrupoId() { return grupoId; }
    public void setGrupoId(UUID grupoId) { this.grupoId = grupoId; }
    public UUID getAsignaturaId() { return asignaturaId; }
    public void setAsignaturaId(UUID asignaturaId) { this.asignaturaId = asignaturaId; }
    public Integer getIntensidadHorariaSemanal() { return intensidadHorariaSemanal; }
    public void setIntensidadHorariaSemanal(Integer intensidadHorariaSemanal) { this.intensidadHorariaSemanal = intensidadHorariaSemanal; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public EstadoCargaAcademica getEstado() { return estado; }
    public void setEstado(EstadoCargaAcademica estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
