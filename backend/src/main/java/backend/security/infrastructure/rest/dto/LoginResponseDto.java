package backend.security.infrastructure.rest.dto;

import java.util.UUID;

public class LoginResponseDto {

    private String token;
    private UUID usuarioId;
    private String username;
    private String tipoContexto;

    public LoginResponseDto(String token, UUID usuarioId, String username, String tipoContexto) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.username = username;
        this.tipoContexto = tipoContexto;
    }

    public String getToken() { return token; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getUsername() { return username; }
    public String getTipoContexto() { return tipoContexto; }
}