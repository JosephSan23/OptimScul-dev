package backend.people.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.people.domain.model.TipoParentesco;

@Entity
@Table(name = "estudiante_acudiente", schema = "optimscul")
public class EstudianteAcudienteEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Column(name = "acudiente_id")
    private UUID acudienteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "parentesco")
    private TipoParentesco parentesco;

    @Column(name = "parentesco_id")
    private UUID parentescoId;

    @Column(name = "es_principal")
    private Boolean esPrincipal;

    @Column(name = "autorizado_recogida")
    private Boolean autorizadoRecogida;

    @Column(name = "autorizado_info_academica")
    private Boolean autorizadoInfoAcademica;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EstudianteAcudienteEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getAcudienteId() { return acudienteId; }
    public void setAcudienteId(UUID acudienteId) { this.acudienteId = acudienteId; }
    public TipoParentesco getParentesco() { return parentesco; }
    public void setParentesco(TipoParentesco parentesco) { this.parentesco = parentesco; }
    public UUID getParentescoId() { return parentescoId; }
    public void setParentescoId(UUID parentescoId) { this.parentescoId = parentescoId; }
    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }
    public Boolean getAutorizadoRecogida() { return autorizadoRecogida; }
    public void setAutorizadoRecogida(Boolean autorizadoRecogida) { this.autorizadoRecogida = autorizadoRecogida; }
    public Boolean getAutorizadoInfoAcademica() { return autorizadoInfoAcademica; }
    public void setAutorizadoInfoAcademica(Boolean autorizadoInfoAcademica) { this.autorizadoInfoAcademica = autorizadoInfoAcademica; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
