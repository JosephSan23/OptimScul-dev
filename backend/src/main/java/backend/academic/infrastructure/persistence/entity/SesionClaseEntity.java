package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import backend.academic.domain.model.EstadoSesionClase;

@Entity
@Table(name = "sesion_clase", schema = "optimscul")
public class SesionClaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "carga_academica_id")
    private UUID cargaAcademicaId;

    @Column(name = "horario_carga_id")
    private UUID horarioCargaId;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Column(name = "tema")
    private String tema;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoSesionClase estado;

    @Column(name = "fue_reprogramada")
    private Boolean fueReprogramada;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public SesionClaseEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getCargaAcademicaId() { return cargaAcademicaId; }
    public void setCargaAcademicaId(UUID cargaAcademicaId) { this.cargaAcademicaId = cargaAcademicaId; }
    public UUID getHorarioCargaId() { return horarioCargaId; }
    public void setHorarioCargaId(UUID horarioCargaId) { this.horarioCargaId = horarioCargaId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public EstadoSesionClase getEstado() { return estado; }
    public void setEstado(EstadoSesionClase estado) { this.estado = estado; }
    public Boolean getFueReprogramada() { return fueReprogramada; }
    public void setFueReprogramada(Boolean fueReprogramada) { this.fueReprogramada = fueReprogramada; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
