package backend.chat.application.service;

import backend.chat.application.port.ConversacionRepository;
import backend.chat.application.port.DirectorioUsuarioPort;
import backend.chat.application.port.MensajeRepository;
import backend.notification.application.port.NotificacionPort;
import backend.chat.domain.model.Conversacion;
import backend.chat.domain.model.Mensaje;
import backend.notification.domain.model.CanalNotificacion;
import backend.notification.domain.model.TipoNotificacion;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatService {

    /** Roles habilitados para el chat (ampliable en el futuro). */

    private final ConversacionRepository conversacionRepository;
    private final MensajeRepository mensajeRepository;
    private final DirectorioUsuarioPort directorio;
    private final NotificacionPort notificacionPort;

    public ChatService(ConversacionRepository conversacionRepository,
                       MensajeRepository mensajeRepository,
                       DirectorioUsuarioPort directorio,
                       NotificacionPort notificacionPort) {
        this.conversacionRepository = conversacionRepository;
        this.mensajeRepository = mensajeRepository;
        this.directorio = directorio;
        this.notificacionPort = notificacionPort;
    }

    // ---- Consultas ----

    public List<DirectorioUsuarioPort.Contacto> contactos(UUID usuarioActual) {
        UUID inst = institucionDe(usuarioActual);
        return directorio.contactos(usuarioActual, inst);
    }   

    @Transactional(readOnly = true)
    public List<ResumenConversacion> listarConversaciones(UUID usuarioActual) {
        institucionDe(usuarioActual);
        List<ResumenConversacion> out = new ArrayList<>();
        for (Conversacion c : conversacionRepository.findAllDeUsuario(usuarioActual)) {
            UUID otro = c.interlocutorDe(usuarioActual);
            Optional<Mensaje> ultimo = mensajeRepository.findUltimo(c.getId());
            out.add(new ResumenConversacion(
                    c.getId(),
                    otro,
                    directorio.nombreCompleto(otro),
                    ultimo.map(Mensaje::getContenido).orElse(null),
                    ultimo.map(Mensaje::getCreatedAt).orElse(c.getUpdatedAt()),
                    mensajeRepository.contarNoLeidos(c.getId(), usuarioActual)
            ));
        }
        out.sort(Comparator.comparing(ResumenConversacion::fecha,
                Comparator.nullsLast(Comparator.reverseOrder())));
        return out;
    }

    @Transactional
    public List<Mensaje> historial(UUID usuarioActual, UUID conversacionId) {
        Conversacion c = conversacionParticipante(usuarioActual, conversacionId);
        List<Mensaje> mensajes = mensajeRepository.findByConversacion(c.getId());
        mensajeRepository.marcarLeidos(c.getId(), usuarioActual); // lo que recibí queda leído
        return mensajes;
    }

    // ---- Comandos ----

    @Transactional
    public Conversacion abrirConversacion(UUID usuarioActual, UUID destinatarioId) {
        if (usuarioActual.equals(destinatarioId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No puedes chatear contigo mismo");
        }
        UUID inst = institucionDe(usuarioActual);
        if (!directorio.puedenChatear(usuarioActual, destinatarioId, inst)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes una relación académica que habilite este chat");
        }
        UUID menor = usuarioActual.compareTo(destinatarioId) <= 0 ? usuarioActual : destinatarioId;
        UUID mayor = menor.equals(usuarioActual) ? destinatarioId : usuarioActual;

        return conversacionRepository.findByPar(inst, menor, mayor).orElseGet(() -> {
            Conversacion nueva = new Conversacion();
            nueva.setId(UUID.randomUUID());
            nueva.setInstitucionId(inst);
            nueva.setUsuarioMenorId(menor);
            nueva.setUsuarioMayorId(mayor);
            nueva.setCreatedBy(usuarioActual);
            nueva.setCreatedAt(java.time.LocalDateTime.now());
            nueva.setUpdatedAt(java.time.LocalDateTime.now());
            return conversacionRepository.save(nueva);
        });
    }

    @Transactional
    public Mensaje enviarMensaje(UUID usuarioActual, UUID conversacionId, String contenido) {
        if (contenido == null || contenido.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El mensaje está vacío");
        }
        Conversacion c = conversacionParticipante(usuarioActual, conversacionId);

        Mensaje m = new Mensaje();
        m.setId(UUID.randomUUID());
        m.setConversacionId(c.getId());
        m.setRemitenteId(usuarioActual);
        m.setContenido(contenido.trim());
        m.setLeido(false);
        m.setCreatedAt(LocalDateTime.now());
        Mensaje guardado = mensajeRepository.save(m);

        c.setUpdatedAt(LocalDateTime.now());
        conversacionRepository.save(c);
        UUID otro = c.interlocutorDe(usuarioActual);
        String remitente = directorio.nombreCompleto(usuarioActual);
        String resumen = contenido.length() > 120 ? contenido.substring(0, 117) + "…" : contenido;
        notificacionPort.notificar(new NotificacionPort.NuevaNotificacion(
        c.getInstitucionId(), TipoNotificacion.GENERAL,
        "Nuevo mensaje de " + remitente, resumen,
        "chat", c.getId(), (short) 1,
        List.of(otro),
        Set.of(CanalNotificacion.IN_APP),
        usuarioActual));
        return guardado;
    }

    /** El otro participante de la conversación (para saber a quién notificar por WebSocket). */
    public UUID interlocutor(UUID usuarioActual, UUID conversacionId) {
        return conversacionParticipante(usuarioActual, conversacionId).interlocutorDe(usuarioActual);
    }

    // ---- Helpers ----

    private Conversacion conversacionParticipante(UUID usuarioActual, UUID conversacionId) {
        Conversacion c = conversacionRepository.findById(conversacionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversación no encontrada"));
        if (!c.participa(usuarioActual)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No perteneces a esta conversación");
        }
        return c;
    }

    private UUID institucionDe(UUID usuarioActual) {
            return directorio.institucionActiva(usuarioActual)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario sin institución activa"));
    }

    public record ResumenConversacion(
            UUID conversacionId,
            UUID interlocutorId,
            String interlocutorNombre,
            String ultimoMensaje,
            LocalDateTime fecha,
            long noLeidos) {}
}