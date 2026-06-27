package backend.admission.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.admission.domain.model.CanalPostulacion;
import backend.admission.domain.model.EstadoPostulacion;

@Entity
@Table(name = "postulacion", schema = "optimscul")
public class PostulacionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "anio_lectivo_id")
    private UUID anioLectivoId;

    @Column(name = "sede_id")
    private UUID sedeId;

    @Column(name = "jornada_id")
    private UUID jornadaId;

    @Column(name = "grado_aspira_id")
    private UUID gradoAspiraId;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "fecha_postulacion")
    private LocalDateTime fechaPostulacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal")
    private CanalPostulacion canal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPostulacion estado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "observaciones_internas")
    private String observacionesInternas;

    @Column(name = "cupo_reservado")
    private Boolean cupoReservado;

    @Column(name = "aprobada_por_usuario_id")
    private UUID aprobadaPorUsuarioId;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(name = "rechazada_por_usuario_id")
    private UUID rechazadaPorUsuarioId;

    @Column(name = "fecha_rechazo")
    private LocalDateTime fechaRechazo;

    @Column(name = "motivo_rechazo")
    private String motivoRechazo;

    @Column(name = "convertida_en_estudiante_id")
    private UUID convertidaEnEstudianteId;

    @Column(name = "convertida_en_matricula_id")
    private UUID convertidaEnMatriculaId;

    @Column(name = "fecha_conversion")
    private LocalDateTime fechaConversion;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PostulacionEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
