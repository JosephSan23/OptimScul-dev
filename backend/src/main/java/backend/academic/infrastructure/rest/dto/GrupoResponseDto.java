package backend.academic.infrastructure.rest.dto;

import java.util.UUID;
import backend.academic.domain.model.EstadoGrupo;

public class GrupoResponseDto {

    private UUID institucionId;

    private UUID anioLectivoId;

    private UUID sedeId;

    private UUID jornadaId;

    private UUID gradoId;

    private String codigo;

    private String nombre;

    private Integer cupoMaximo;

    private EstadoGrupo estado;

    private String observaciones;

    public GrupoResponseDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getSedeId() { return sedeId; }
    public void setSedeId(UUID sedeId) { this.sedeId = sedeId; }
    public UUID getJornadaId() { return jornadaId; }
    public void setJornadaId(UUID jornadaId) { this.jornadaId = jornadaId; }
    public UUID getGradoId() { return gradoId; }
    public void setGradoId(UUID gradoId) { this.gradoId = gradoId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }
    public EstadoGrupo getEstado() { return estado; }
    public void setEstado(EstadoGrupo estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
