package backend.security.infrastructure.rest.dto;
import java.util.List;

import java.util.UUID;

public class LoginResponseDto {

    private String token;
    private UUID usuarioId;
    private String username;
    private String tipoContexto;
    private List<String> roles;

    public LoginResponseDto(String token, UUID usuarioId, String username, String tipoContexto, List<String> roles) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.username = username;
        this.tipoContexto = tipoContexto;
        this.roles = roles;
    }

    public String getToken() { return token; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getUsername() { return username; }
    public String getTipoContexto() { return tipoContexto; }
    public List<String> getRoles() { return roles; }
}