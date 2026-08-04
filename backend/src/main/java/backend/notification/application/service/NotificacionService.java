package backend.notification.application.service;

import backend.notification.application.NotificacionVista;
import backend.notification.application.port.*;
import backend.notification.domain.model.*;
import backend.notification.infrastructure.rest.dto.NotificacionDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class NotificacionService implements NotificacionPort {

    private final NotificacionRepository notificacionRepository;
    private final NotificacionDestinatarioRepository destinatarioRepository;
    private final DestinatarioResolverPort resolver;
    private final EmailPort emailPort;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionService(NotificacionRepository notificacionRepository,
                               NotificacionDestinatarioRepository destinatarioRepository,
                               DestinatarioResolverPort resolver,
                               EmailPort emailPort,
                               SimpMessagingTemplate messagingTemplate) {
        this.notificacionRepository = notificacionRepository;
        this.destinatarioRepository = destinatarioRepository;
        this.resolver = resolver;
        this.emailPort = emailPort;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    @Transactional
    public void notificar(NuevaNotificacion nv) {
        if (nv.destinatarios() == null || nv.destinatarios().isEmpty()) return;

        Notificacion n = new Notificacion();
        n.setId(UUID.randomUUID());
        n.setInstitucionId(nv.institucionId());
        n.setTipo(nv.tipo());
        n.setTitulo(nv.titulo());
        n.setMensaje(nv.mensaje());
        n.setModuloRelacionado(nv.moduloRelacionado());
        n.setEntidadRelacionadaId(nv.entidadRelacionadaId());
        n.setPrioridad(nv.prioridad());
        n.setCreatedBy(nv.createdBy());
        n.setCreatedAt(LocalDateTime.now());
        notificacionRepository.save(n);

        Set<CanalNotificacion> canales = (nv.canales() == null || nv.canales().isEmpty())
                ? Set.of(CanalNotificacion.IN_APP) : nv.canales();

        for (UUID uid : new LinkedHashSet<>(nv.destinatarios())) {
            for (CanalNotificacion canal : canales) {
                NotificacionDestinatario d = new NotificacionDestinatario();
                d.setId(UUID.randomUUID());
                d.setNotificacionId(n.getId());
                d.setUsuarioId(uid);
                d.setCanal(canal);
                d.setEstado(EstadoNotificacion.PENDIENTE);
                d.setCreatedAt(LocalDateTime.now());
                d.setUpdatedAt(LocalDateTime.now());
                d = destinatarioRepository.save(d);

                if (canal == CanalNotificacion.IN_APP) {
                    d.setEstado(EstadoNotificacion.ENVIADA);
                    d.setEnviadaEn(LocalDateTime.now());
                    destinatarioRepository.save(d);
                    messagingTemplate.convertAndSendToUser(
                            uid.toString(), "/queue/notificaciones", toDto(d, n));
                } else if (canal == CanalNotificacion.EMAIL) {
                    emailPort.enviar(d.getId(), uid, n.getTitulo(), n.getMensaje()); // async
                }
            }
        }
    }

    /** Atajo para eventos académicos: notifica al estudiante y a sus acudientes (IN_APP + Email). */
    public void notificarEventoAcademico(UUID institucionId, UUID estudianteId,
                                         String titulo, String mensaje,
                                         String modulo, UUID entidadId, UUID createdBy) {
        List<UUID> dest = resolver.usuariosDeEstudianteYAcudientes(estudianteId);
        if (dest.isEmpty()) return;
        notificar(new NuevaNotificacion(institucionId, TipoNotificacion.ACADEMICA,
                titulo, mensaje, modulo, entidadId, (short) 1,
                dest, Set.of(CanalNotificacion.IN_APP, CanalNotificacion.EMAIL), createdBy));
    }

    // ---- lectura / marcado ----
    @Transactional(readOnly = true)
    public List<NotificacionVista> bandeja(UUID usuarioId) { return destinatarioRepository.bandeja(usuarioId, 30); }
    @Transactional(readOnly = true)
    public long noLeidas(UUID usuarioId) { return destinatarioRepository.contarNoLeidas(usuarioId); }
    @Transactional
    public void marcarLeida(UUID id, UUID usuarioId) { destinatarioRepository.marcarLeida(id, usuarioId); }
    @Transactional
    public void marcarTodas(UUID usuarioId) { destinatarioRepository.marcarTodas(usuarioId); }
    @Transactional
    public void marcarLeidasDeConversacion(UUID usuarioId, UUID convId) {
        destinatarioRepository.marcarLeidasDeConversacion(usuarioId, convId);
    }

    private NotificacionDto toDto(NotificacionDestinatario d, Notificacion n) {
        return new NotificacionDto(d.getId(), n.getId(), n.getTipo().name(), n.getTitulo(), n.getMensaje(),
                n.getModuloRelacionado(), n.getEntidadRelacionadaId(),
                n.getPrioridad() == null ? null : n.getPrioridad().intValue(),
                d.getEstado().name(), d.getLeidaEn(), n.getCreatedAt());
    }

    public void notificarEventoDeGrupo(UUID institucionId, UUID grupoId, String titulo, String mensaje,
                                   String modulo, UUID entidadId, UUID createdBy) {
    List<UUID> dest = resolver.usuariosDeGrupo(grupoId);
    if (dest.isEmpty()) return;
    notificar(new NuevaNotificacion(institucionId, TipoNotificacion.ACADEMICA,
            titulo, mensaje, modulo, entidadId, (short) 1,
            dest, Set.of(CanalNotificacion.IN_APP, CanalNotificacion.EMAIL), createdBy));
    }

    public void notificarBoletinDisponible(UUID institucionId, UUID anioLectivoId,
                                       UUID periodoId, String periodoNombre, UUID createdBy) {
    List<UUID> dest = resolver.usuariosDeInstitucionAnio(institucionId, anioLectivoId);
    if (dest.isEmpty()) return;
    notificar(new NuevaNotificacion(institucionId, TipoNotificacion.ACADEMICA,
            "Boletín disponible",
            "Ya puedes consultar el boletín del " + periodoNombre + ".",
            "academic", periodoId, (short) 1,
            dest, Set.of(CanalNotificacion.IN_APP, CanalNotificacion.EMAIL), createdBy));
}
}