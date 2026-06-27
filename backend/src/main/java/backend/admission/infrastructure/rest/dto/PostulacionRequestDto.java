package backend.admission.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.admission.domain.model.CanalPostulacion;
import backend.admission.domain.model.EstadoPostulacion;

public class PostulacionRequestDto {

    @NotNull
    private UUID institucionId;

    private UUID anioLectivoId;

    private UUID sedeId;

    private UUID jornadaId;

    private UUID gradoAspiraId;

    @NotNull
    private String codigo;

    @NotNull
    private LocalDateTime fechaPostulacion;

    @NotNull
    private CanalPostulacion canal;

    @NotNull
    private EstadoPostulacion estado;

    private String observaciones;

    private String observacionesInternas;

    @NotNull
    private Boolean cupoReservado;

    private UUID aprobadaPorUsuarioId;

    private LocalDateTime fechaAprobacion;

    private UUID rechazadaPorUsuarioId;

    private LocalDateTime fechaRechazo;

    private String motivoRechazo;

    private UUID convertidaEnEstudianteId;

    private UUID convertidaEnMatriculaId;

    private LocalDateTime fechaConversion;

    public PostulacionRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getSedeId() { return sedeId; }
    public void setSedeId(UUID sedeId) { this.sedeId = sedeId; }
    public UUID getJornadaId() { return jornadaId; }
    public void setJornadaId(UUID jornadaId) { this.jornadaId = jornadaId; }
    public UUID getGradoAspiraId() { return gradoAspiraId; }
    public void setGradoAspiraId(UUID gradoAspiraId) { this.gradoAspiraId = gradoAspiraId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDateTime getFechaPostulacion() { return fechaPostulacion; }
    public void setFechaPostulacion(LocalDateTime fechaPostulacion) { this.fechaPostulacion = fechaPostulacion; }
    public CanalPostulacion getCanal() { return canal; }
    public void setCanal(CanalPostulacion canal) { this.canal = canal; }
    public EstadoPostulacion getEstado() { return estado; }
    public void setEstado(EstadoPostulacion estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getObservacionesInternas() { return observacionesInternas; }
    public void setObservacionesInternas(String observacionesInternas) { this.observacionesInternas = observacionesInternas; }
    public Boolean getCupoReservado() { return cupoReservado; }
    public void setCupoReservado(Boolean cupoReservado) { this.cupoReservado = cupoReservado; }
    public UUID getAprobadaPorUsuarioId() { return aprobadaPorUsuarioId; }
    public void setAprobadaPorUsuarioId(UUID aprobadaPorUsuarioId) { this.aprobadaPorUsuarioId = aprobadaPorUsuarioId; }
    public LocalDateTime getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(LocalDateTime fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }
    public UUID getRechazadaPorUsuarioId() { return rechazadaPorUsuarioId; }
    public void setRechazadaPorUsuarioId(UUID rechazadaPorUsuarioId) { this.rechazadaPorUsuarioId = rechazadaPorUsuarioId; }
    public LocalDateTime getFechaRechazo() { return fechaRechazo; }
    public void setFechaRechazo(LocalDateTime fechaRechazo) { this.fechaRechazo = fechaRechazo; }
    public String getMotivoRechazo() { return motivoRechazo; }
    public void setMotivoRechazo(String motivoRechazo) { this.motivoRechazo = motivoRechazo; }
    public UUID getConvertidaEnEstudianteId() { return convertidaEnEstudianteId; }
    public void setConvertidaEnEstudianteId(UUID convertidaEnEstudianteId) { this.convertidaEnEstudianteId = convertidaEnEstudianteId; }
    public UUID getConvertidaEnMatriculaId() { return convertidaEnMatriculaId; }
    public void setConvertidaEnMatriculaId(UUID convertidaEnMatriculaId) { this.convertidaEnMatriculaId = convertidaEnMatriculaId; }
    public LocalDateTime getFechaConversion() { return fechaConversion; }
    public void setFechaConversion(LocalDateTime fechaConversion) { this.fechaConversion = fechaConversion; }
}
