package backend.security.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.TipoContextoUsuario;

public class UsuarioRequestDto {

    @NotNull
    private UUID personaId;

    @NotNull
    private String username;

    @NotNull
    private String passwordHash;

    private String emailLogin;

    @NotNull
    private String tipoContexto;

    @NotNull
    private EstadoUsuario estado;

    @NotNull
    private Boolean requiereCambioPassword;

    @NotNull
    private Boolean emailVerificado;

    @NotNull
    private Boolean dobleFactorHabilitado;

    @NotNull
    private Integer intentosFallidos;

    private LocalDateTime bloqueadoHasta;

    private LocalDateTime ultimoLogin;

    private LocalDateTime ultimoCambioPassword;

    public UsuarioRequestDto() {}

    public UUID getPersonaId() { return personaId; }
    public void setPersonaId(UUID personaId) { this.personaId = personaId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getEmailLogin() { return emailLogin; }
    public void setEmailLogin(String emailLogin) { this.emailLogin = emailLogin; }
    public String getTipoContexto() { return tipoContexto; }
    public void setTipoContexto(String tipoContexto) { this.tipoContexto = tipoContexto; }
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
}
