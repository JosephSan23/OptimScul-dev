package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import backend.academic.domain.model.NivelAcademico;
import backend.people.domain.model.EstadoRegistro;

public class GradoRequestDto {

    @NotNull
    private UUID institucionId;

    @NotNull
    private String codigo;

    @NotNull
    private String nombre;

    @NotNull
    private NivelAcademico nivel;

    @NotNull
    private Integer orden;

    @NotNull
    private EstadoRegistro estado;

    public GradoRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public NivelAcademico getNivel() { return nivel; }
    public void setNivel(NivelAcademico nivel) { this.nivel = nivel; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public EstadoRegistro getEstado() { return estado; }
    public void setEstado(EstadoRegistro estado) { this.estado = estado; }
}
