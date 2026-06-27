package backend.people.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import backend.people.domain.model.TipoParentesco;

public class EstudianteAcudienteRequestDto {

    @NotNull
    private UUID estudianteId;

    @NotNull
    private UUID acudienteId;

    @NotNull
    private TipoParentesco parentesco;

    private UUID parentescoId;

    @NotNull
    private Boolean esPrincipal;

    @NotNull
    private Boolean autorizadoRecogida;

    @NotNull
    private Boolean autorizadoInfoAcademica;

    private String observaciones;

    public EstudianteAcudienteRequestDto() {}

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
}
