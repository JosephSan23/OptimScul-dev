package backend.academic.infrastructure.rest.dto.Grupo;

import backend.academic.domain.model.Grupo;
import java.util.UUID;

public class GrupoResponseDto {

    private UUID id;
    private UUID gradoId;
    private UUID anioLectivoId;
    private UUID sedeId;
    private UUID jornadaId;
    private String codigo;
    private String nombre;
    private Integer cupoMaximo;
    private String estado;
    private String observaciones;

    public GrupoResponseDto() {}

    public static GrupoResponseDto desde(Grupo g) {
        GrupoResponseDto d = new GrupoResponseDto();
        d.setId(g.getId());
        d.setGradoId(g.getGradoId());
        d.setAnioLectivoId(g.getAnioLectivoId());
        d.setSedeId(g.getSedeId());
        d.setJornadaId(g.getJornadaId());
        d.setCodigo(g.getCodigo());
        d.setNombre(g.getNombre());
        d.setCupoMaximo(g.getCupoMaximo());
        d.setEstado(g.getEstado() != null ? g.getEstado().name() : null);
        d.setObservaciones(g.getObservaciones());
        return d;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getGradoId() { return gradoId; }
    public void setGradoId(UUID gradoId) { this.gradoId = gradoId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getSedeId() { return sedeId; }
    public void setSedeId(UUID sedeId) { this.sedeId = sedeId; }
    public UUID getJornadaId() { return jornadaId; }
    public void setJornadaId(UUID jornadaId) { this.jornadaId = jornadaId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}