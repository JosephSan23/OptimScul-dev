package backend.security.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.TipoContextoUsuario;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@NoArgsConstructor
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
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo_contexto", columnDefinition = "tipo_contexto_usuario_enum")
    private TipoContextoUsuario tipoContexto;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado", columnDefinition = "estado_usuario_enum")
    private EstadoUsuario estado;

    @Column(name = "requiere_cambio_password")
    private Boolean requiereCambioPassword;

    @Column(name = "email_verificado")
    private Boolean emailVerificado;

    @Column(name = "doble_factor_habilitado")
    private Boolean dobleFactorHabilitado;

    @Column(name = "intentos_fallidos")
    private Short intentosFallidos;

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
}
