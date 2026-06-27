package backend.security.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.TipoContextoUsuario;

@Entity
@Table(name = "usuario", schema = "optimscul")
public class UsuarioEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "persona_id")
    private UUID personaId;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email_login")
    private String emailLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contexto")
    private TipoContextoUsuario tipoContexto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoUsuario estado;

    @Column(name = "requiere_cambio_password")
    private Boolean requiereCambioPassword;

    @Column(name = "email_verificado")
    private Boolean emailVerificado;

    @Column(name = "doble_factor_habilitado")
    private Boolean dobleFactorHabilitado;

    @Column(name = "intentos_fallidos")
    private Integer intentosFallidos;

    @Column(name = "bloqueado_hasta")
    private LocalDateTime bloqueadoHasta;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "ultimo_cambio_password")
    private LocalDateTime ultimoCambioPassword;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UsuarioEntity() {}

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
