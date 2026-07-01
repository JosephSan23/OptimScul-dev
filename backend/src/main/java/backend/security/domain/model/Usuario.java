package backend.security.domain.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
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
    private Short intentosFallidos;
    private LocalDateTime bloqueadoHasta;
    private LocalDateTime ultimoLogin;
    private LocalDateTime ultimoCambioPassword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
