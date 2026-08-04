package backend.notification.infrastructure.email;

import backend.notification.application.port.DestinatarioResolverPort;
import backend.notification.application.port.EmailPort;
import backend.notification.application.port.NotificacionDestinatarioRepository;
import backend.notification.domain.model.EstadoNotificacion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailSender implements EmailPort {

    private final DestinatarioResolverPort resolver;
    private final NotificacionDestinatarioRepository destinatarioRepository;
    private final ObjectProvider<JavaMailSender> mailProvider;

    @Value("${notificaciones.email.habilitado:false}")
    private boolean habilitado;
    @Value("${notificaciones.email.remitente:no-reply@optimscul.com}")
    private String remitente;

    public EmailSender(DestinatarioResolverPort resolver,
            NotificacionDestinatarioRepository destinatarioRepository, ObjectProvider<JavaMailSender> mailProvider) {
        this.resolver = resolver;
        this.destinatarioRepository = destinatarioRepository;
        this.mailProvider = mailProvider;
    }

    @Override
    @Async
    public void enviar(UUID destinatarioId, UUID usuarioId, String titulo, String mensaje) {
        JavaMailSender mail = mailProvider.getIfAvailable();
        if (!habilitado || mail == null)
            return; // se activa cuando tengas SMTP listo
        try {
            String to = resolver.emailDe(usuarioId)
                    .orElseThrow(() -> new IllegalStateException("Usuario sin correo"));
            SimpleMailMessage m = new SimpleMailMessage();
            m.setFrom(remitente);
            m.setTo(to);
            m.setSubject(titulo);
            m.setText(mensaje);
            mail.send(m);
            destinatarioRepository.actualizarEstado(destinatarioId, EstadoNotificacion.ENVIADA, null);
        } catch (Exception e) {
            destinatarioRepository.actualizarEstado(destinatarioId, EstadoNotificacion.FALLIDA, e.getMessage());
        }
    }
}