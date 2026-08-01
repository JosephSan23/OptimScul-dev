package backend.chat.infrastructure.websocket;

import backend.security.infrastructure.security.JwtService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    public JwtChannelInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Falta token en la conexión WebSocket");
            }
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (username == null || !jwtService.isTokenValid(token, username)) {
                throw new IllegalArgumentException("Token inválido en la conexión WebSocket");
            }
            UUID usuarioId = jwtService.extractUsuarioId(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    usuarioId, null, Collections.emptyList());
            accessor.setUser(auth); // getName() = usuarioId.toString()
        }
        return message;
    }
}