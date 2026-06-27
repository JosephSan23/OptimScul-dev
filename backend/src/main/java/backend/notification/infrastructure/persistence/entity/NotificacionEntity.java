package backend.notification.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.notification.domain.model.TipoNotificacion;

@Entity
@Table(name = "notificacion", schema = "optimscul")
public class NotificacionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoNotificacion tipo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "modulo_relacionado")
    private String moduloRelacionado;

    @Column(name = "entidad_relacionada_id")
    private UUID entidadRelacionadaId;

    @Column(name = "prioridad")
    private Short prioridad;

    @Column(name = "programada_para")
    private LocalDateTime programadaPara;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public NotificacionEntity() {}

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
