package backend.chat.infrastructure.rest.controller;

import backend.chat.application.service.ChatService;
import backend.chat.application.port.DirectorioUsuarioPort;
import backend.chat.domain.model.Conversacion;
import backend.chat.domain.model.Mensaje;
import backend.chat.infrastructure.rest.dto.AbrirConversacionRequestDto;
import backend.chat.infrastructure.rest.dto.EnviarMensajeRequestDto;
import backend.chat.infrastructure.rest.dto.MensajeResponseDto;
import jakarta.validation.Valid;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/contactos")
    public List<DirectorioUsuarioPort.Contacto> contactos(@AuthenticationPrincipal UUID usuarioId) {
        return chatService.contactos(usuarioId);
    }

    @GetMapping("/conversaciones")
    public List<ChatService.ResumenConversacion> conversaciones(@AuthenticationPrincipal UUID usuarioId) {
        return chatService.listarConversaciones(usuarioId);
    }

    @PostMapping("/conversaciones")
    public ChatService.ResumenConversacion abrir(@AuthenticationPrincipal UUID usuarioId,
                                                 @Valid @RequestBody AbrirConversacionRequestDto body) {
        Conversacion c = chatService.abrirConversacion(usuarioId, body.getDestinatarioId());
        // devuelve el resumen ya normalizado
        return chatService.listarConversaciones(usuarioId).stream()
                .filter(r -> r.conversacionId().equals(c.getId()))
                .findFirst()
                .orElseThrow();
    }

    @GetMapping("/conversaciones/{id}/mensajes")
    public List<MensajeResponseDto> historial(@AuthenticationPrincipal UUID usuarioId,
                                              @PathVariable UUID id) {
        return chatService.historial(usuarioId, id).stream().map(MensajeResponseDto::de).toList();
    }

    @PostMapping("/conversaciones/{id}/mensajes")
    public MensajeResponseDto enviar(@AuthenticationPrincipal UUID usuarioId,
                                     @PathVariable UUID id,
                                     @Valid @RequestBody EnviarMensajeRequestDto body) {
        Mensaje m = chatService.enviarMensaje(usuarioId, id, body.getContenido());
        MensajeResponseDto dto = MensajeResponseDto.de(m);

        // Empuja en tiempo real al destinatario y al propio remitente (otras pestañas)
        UUID otro = chatService.interlocutor(usuarioId, id);
        messagingTemplate.convertAndSendToUser(otro.toString(), "/queue/mensajes", dto);
        messagingTemplate.convertAndSendToUser(usuarioId.toString(), "/queue/mensajes", dto);
        return dto;
    }
}