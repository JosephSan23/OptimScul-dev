package backend.coexistence.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.coexistence.domain.model.EstadoObservacion;
import backend.coexistence.domain.model.SeveridadObservacion;

public class ObservacionEstudianteRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private UUID estudianteId;

    private UUID anioLectivoId;

    private UUID periodoAcademicoId;

    private UUID grupoId;

    private UUID cargaAcademicaId;

    @NotNull
    private UUID tipoObservacionId;

    @NotNull
    private String titulo;

    @NotNull
    private String descripcion;

    @NotNull
    private LocalDate fechaEvento;

    @NotNull
    private SeveridadObservacion severidad;

    @NotNull
    private EstadoObservacion estado;

    @NotNull
    private Boolean visibleAcudiente;

    @NotNull
    private Boolean requiereSeguimiento;

    private LocalDateTime cerradaEn;

    private UUID creadaPorUsuarioId;

    private UUID cerradaPorUsuarioId;

    public ObservacionEstudianteRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(UUID periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }
    public UUID getGrupoId() { return grupoId; }
    public void setGrupoId(UUID grupoId) { this.grupoId = grupoId; }
    public UUID getCargaAcademicaId() { return cargaAcademicaId; }
    public void setCargaAcademicaId(UUID cargaAcademicaId) { this.cargaAcademicaId = cargaAcademicaId; }
    public UUID getTipoObservacionId() { return tipoObservacionId; }
    public void setTipoObservacionId(UUID tipoObservacionId) { this.tipoObservacionId = tipoObservacionId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(LocalDate fechaEvento) { this.fechaEvento = fechaEvento; }
    public SeveridadObservacion getSeveridad() { return severidad; }
    public void setSeveridad(SeveridadObservacion severidad) { this.severidad = severidad; }
    public EstadoObservacion getEstado() { return estado; }
    public void setEstado(EstadoObservacion estado) { this.estado = estado; }
    public Boolean getVisibleAcudiente() { return visibleAcudiente; }
    public void setVisibleAcudiente(Boolean visibleAcudiente) { this.visibleAcudiente = visibleAcudiente; }
    public Boolean getRequiereSeguimiento() { return requiereSeguimiento; }
    public void setRequiereSeguimiento(Boolean requiereSeguimiento) { this.requiereSeguimiento = requiereSeguimiento; }
    public LocalDateTime getCerradaEn() { return cerradaEn; }
    public void setCerradaEn(LocalDateTime cerradaEn) { this.cerradaEn = cerradaEn; }
    public UUID getCreadaPorUsuarioId() { return creadaPorUsuarioId; }
    public void setCreadaPorUsuarioId(UUID creadaPorUsuarioId) { this.creadaPorUsuarioId = creadaPorUsuarioId; }
    public UUID getCerradaPorUsuarioId() { return cerradaPorUsuarioId; }
    public void setCerradaPorUsuarioId(UUID cerradaPorUsuarioId) { this.cerradaPorUsuarioId = cerradaPorUsuarioId; }
}
