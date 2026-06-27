package backend.security.domain.model;

import java.time.LocalDateTime;
import backend.security.domain.model.TipoContextoUsuario;
import java.util.UUID;

public class Usuario {

    private UUID id;
    private UUID personaId;
    private String username;
    private String passwordHash;
    private String emailLogin;
    private TipoContextoUsuario tipoContexto;
    private EstadoUsuario estado;
    private Boolean requiereCambioPassword;
    private Boolean emailVerificado;
    private Boolean dobleFactorHabilitado;
    private Integer intentosFallidos;
    private LocalDateTime bloqueadoHasta;
    private LocalDateTime ultimoLogin;
    private LocalDateTime ultimoCambioPassword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Usuario() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPersonaId() { return personaId; }
    public void setPersonaId(UUID personaId) { this.personaId = personaId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getEmailLogin() { return emailLogin; }
    public void setEmailLogin(String emailLogin) { this.emailLogin = emailLogin; }
    public TipoContextoUsuario getTipoContexto() { return tipoContexto; }
    public void setTipoContexto(TipoContextoUsuario tipoContexto) { this.tipoContexto = tipoContexto; }
    public EstadoUsuario getEstado() { return estado; }
    public void setEstado(EstadoUsuario estado) { this.estado = estado; }
    public Boolean getRequiereCambioPassword() { return requiereCambioPassword; }
    public void setRequiereCambioPassword(Boolean requiereCambioPassword) { this.requiereCambioPassword = requiereCambioPassword; }
    public Boolean getEmailVerificado() { return emailVerificado; }
    public void setEmailVerificado(Boolean emailVerificado) { this.emailVerificado = emailVerificado; }
    public Boolean getDobleFactorHabilitado() { return dobleFactorHabilitado; }
    public void setDobleFactorHabilitado(Boolean dobleFactorHabilitado) { this.dobleFactorHabilitado = dobleFactorHabilitado; }
    public Integer getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(Integer intentosFallidos) { this.intentosFallidos = intentosFallidos; }
    public LocalDateTime getBloqueadoHasta() { return bloqueadoHasta; }
    public void setBloqueadoHasta(LocalDateTime bloqueadoHasta) { this.bloqueadoHasta = bloqueadoHasta; }
    public LocalDateTime getUltimoLogin() { return ultimoLogin; }
    public void setUltimoLogin(LocalDateTime ultimoLogin) { this.ultimoLogin = ultimoLogin; }
    public LocalDateTime getUltimoCambioPassword() { return ultimoCambioPassword; }
    public void setUltimoCambioPassword(LocalDateTime ultimoCambioPassword) { this.ultimoCambioPassword = ultimoCambioPassword; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
