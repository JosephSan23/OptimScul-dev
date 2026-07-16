package backend.academic.infrastructure.rest.dto.Grado;

import backend.academic.domain.model.Grado;
import java.util.UUID;

public class GradoResponseDto {

    private UUID id;
    private String codigo;
    private String nombre;
    private String nivel;
    private Short orden;
    private String estado;

    public GradoResponseDto() {}

    public static GradoResponseDto desde(Grado g) {
        GradoResponseDto d = new GradoResponseDto();
        d.setId(g.getId());
        d.setCodigo(g.getCodigo());
        d.setNombre(g.getNombre());
        d.setNivel(g.getNivel() != null ? g.getNivel().name() : null);
        d.setOrden(g.getOrden());
        d.setEstado(g.getEstado() != null ? g.getEstado().name() : null);
        return d;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public Short getOrden() { return orden; }
    public void setOrden(Short orden) { this.orden = orden; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}