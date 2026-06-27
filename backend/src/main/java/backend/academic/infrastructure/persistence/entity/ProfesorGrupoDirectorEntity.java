package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "profesor_grupo_director", schema = "optimscul")
public class ProfesorGrupoDirectorEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "grupo_id")
    private UUID grupoId;

    @Column(name = "profesor_id")
    private UUID profesorId;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ProfesorGrupoDirectorEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getGrupoId() { return grupoId; }
    public void setGrupoId(UUID grupoId) { this.grupoId = grupoId; }
    public UUID getProfesorId() { return profesorId; }
    public void setProfesorId(UUID profesorId) { this.profesorId = profesorId; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
