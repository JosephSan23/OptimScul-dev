package backend.notification.application.port;

import java.util.UUID;

public interface EmailPort {

    /** Envío asíncrono ligado a una notificación (resuelve el correo del usuario internamente). */
    void enviar(UUID destinatarioId, UUID usuarioId, String titulo, String mensaje);

    /**
     * Envío directo y SINCRÓNICO a una dirección de correo explícita.
     * Se usa para enviar credenciales de acceso, donde el destino debe ser el correo
     * personal alcanzable (no el email_login institucional al que aún no pueden entrar).
     *
     * @return true si el correo se envió; false si el envío está deshabilitado,
     *         no hay servidor SMTP configurado, el correo es nulo/vacío o el envío falló.
     */
    boolean enviarDirecto(String correo, String titulo, String mensaje);
}
