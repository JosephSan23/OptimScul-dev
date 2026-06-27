package backend.notification.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.notification.domain.model.TipoNotificacion;

public class NotificacionRequestDto {

    private UUID institucionId;

    @NotNull
    private TipoNotificacion tipo;

    @NotNull
    private String titulo;

    @NotNull
    private String mensaje;

    private String moduloRelacionado;

    private UUID entidadRelacionadaId;

    @NotNull
    private Integer prioridad;

    private LocalDateTime programadaPara;

    public NotificacionRequestDto() {}

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
    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer prioridad) { this.prioridad = prioridad; }
    public LocalDateTime getProgramadaPara() { return programadaPara; }
    public void setProgramadaPara(LocalDateTime programadaPara) { this.programadaPara = programadaPara; }
}
