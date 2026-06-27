package backend.academic.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import backend.people.domain.model.EstadoRegistro;

public class Grado {

    private UUID id;
    private UUID institucionId;
    private String codigo;
    private String nombre;
    private NivelAcademico nivel;
    private Short orden;
    private EstadoRegistro estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Grado() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public NivelAcademico getNivel() { return nivel; }
    public void setNivel(NivelAcademico nivel) { this.nivel = nivel; }
    public Short getOrden() { return orden; }
    public void setOrden(Short orden) { this.orden = orden; }
    public EstadoRegistro getEstado() { return estado; }
    public void setEstado(EstadoRegistro estado) { this.estado = estado; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
