package backend.people.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class EstudianteAcudiente {

    private UUID id;
    private UUID estudianteId;
    private UUID acudienteId;
    private TipoParentesco parentesco;
    private UUID parentescoId;
    private Boolean esPrincipal;
    private Boolean autorizadoRecogida;
    private Boolean autorizadoInfoAcademica;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EstudianteAcudiente() {}

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
