package backend.notification.application.port;

import java.util.UUID;

public interface EmailPort {
    void enviar(UUID destinatarioId, UUID usuarioId, String titulo, String mensaje);
}