package backend.notification.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notificacion {

    private UUID id;
    private UUID institucionId;
    private TipoNotificacion tipo;
    private String titulo;
    private String mensaje;
    private String moduloRelacionado;
    private UUID entidadRelacionadaId;
    private Short prioridad;
    private LocalDateTime programadaPara;
    private UUID createdBy;
    private LocalDateTime createdAt;

    public Notificacion() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public TipoNotificacion getTipo() { return tipo; }
    public void setTipo(TipoNotificacion tipo) { this.tipo = tipo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getModuloRelacionado() { return moduloRelacionado; }
    public void setModuloRelacionado(String moduloRelacionado) { this.moduloRelacionado = moduloRelacionado; }
    public UUID getEntidadRelacionadaId() { return entidadRelacionadaId; }
    public void setEntidadRelacionadaId(UUID entidadRelacionadaId) { this.entidadRelacionadaId = entidadRelacionadaId; }
    public Short getPrioridad() { return prioridad; }
    public void setPrioridad(Short prioridad) { this.prioridad = prioridad; }
    public LocalDateTime getProgramadaPara() { return programadaPara; }
    public void setProgramadaPara(LocalDateTime programadaPara) { this.programadaPara = programadaPara; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
