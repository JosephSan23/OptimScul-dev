package backend.enrollment.infrastructure.rest.dto;

import java.time.LocalDate;
import java.util.UUID;
import backend.enrollment.domain.model.EstadoMatricula;
import backend.enrollment.domain.model.TipoMatricula;

public class MatriculaResponseDto {

    private UUID institucionId;

    private UUID estudianteId;

    private UUID anioLectivoId;

    private UUID grupoId;

    private String codigoMatricula;

    private TipoMatricula tipo;

    private EstadoMatricula estado;

    private LocalDate fechaMatricula;

    private LocalDate fechaEstado;

    private String observaciones;

    public MatriculaResponseDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getGrupoId() { return grupoId; }
    public void setGrupoId(UUID grupoId) { this.grupoId = grupoId; }
    public String getCodigoMatricula() { return codigoMatricula; }
    public void setCodigoMatricula(String codigoMatricula) { this.codigoMatricula = codigoMatricula; }
    public TipoMatricula getTipo() { return tipo; }
    public void setTipo(TipoMatricula tipo) { this.tipo = tipo; }
    public EstadoMatricula getEstado() { return estado; }
    public void setEstado(EstadoMatricula estado) { this.estado = estado; }
    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDate fechaMatricula) { this.fechaMatricula = fechaMatricula; }
    public LocalDate getFechaEstado() { return fechaEstado; }
    public void setFechaEstado(LocalDate fechaEstado) { this.fechaEstado = fechaEstado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
