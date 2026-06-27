package backend.people.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import backend.people.domain.model.EstadoAcudiente;

public class AcudienteRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private UUID personaId;

    private String ocupacion;

    private String empresa;

    @NotNull
    private EstadoAcudiente estado;

    private String observaciones;

    public AcudienteRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getPersonaId() { return personaId; }
    public void setPersonaId(UUID personaId) { this.personaId = personaId; }
    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
    public EstadoAcudiente getEstado() { return estado; }
    public void setEstado(EstadoAcudiente estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
