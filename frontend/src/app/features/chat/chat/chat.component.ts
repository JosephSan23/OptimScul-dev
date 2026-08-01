import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import {
  ChatService,
  ResumenConversacion,
  MensajeChat,
  Contacto,
} from '../../../core/services/chat.service';
import { ChatSocketService } from '../../../core/services/chat-socket.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
})
export class ChatComponent implements OnInit, OnDestroy {
  conversaciones: ResumenConversacion[] = [];
  contactos: Contacto[] = [];
  mensajes: MensajeChat[] = [];
  activa?: ResumenConversacion;
  borrador = '';
  miId = '';
  mostrarContactos = false;
  private sub?: Subscription;

  constructor(
    private chat: ChatService,
    private socket: ChatSocketService,
    private auth: AuthService,
  ) {}

  ngOnInit(): void {
    this.miId = this.auth.getUsuarioActual()?.usuarioId ?? '';
    this.cargarConversaciones();
    this.socket.conectar();
    this.sub = this.socket.mensajes.subscribe((m) => this.alRecibir(m));
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
    this.socket.desconectar();
  }

  cargarConversaciones(): void {
    this.chat.conversaciones().subscribe((cs) => (this.conversaciones = cs));
  }

  abrir(c: ResumenConversacion): void {
    this.activa = c;
    c.noLeidos = 0;
    this.chat
      .historial(c.conversacionId)
      .subscribe((ms) => (this.mensajes = ms));
  }

  enviar(): void {
    const texto = this.borrador.trim();
    if (!texto || !this.activa) return;
    this.borrador = '';
    this.chat
      .enviar(this.activa.conversacionId, texto)
      .subscribe((m) => this.agregarSiFalta(m));
  }

  toggleContactos(): void {
    this.mostrarContactos = !this.mostrarContactos;
    if (this.mostrarContactos && this.contactos.length === 0) {
      this.chat.contactos().subscribe((cs) => (this.contactos = cs));
    }
  }

  iniciarCon(contacto: Contacto): void {
    this.chat.abrirConversacion(contacto.usuarioId).subscribe((conv) => {
      this.mostrarContactos = false;
      if (
        !this.conversaciones.some(
          (c) => c.conversacionId === conv.conversacionId,
        )
      ) {
        this.conversaciones.unshift(conv);
      }
      this.abrir(conv);
    });
  }

  esMio(m: MensajeChat): boolean {
    return m.remitenteId === this.miId;
  }

  private alRecibir(m: MensajeChat): void {
    if (this.activa && m.conversacionId === this.activa.conversacionId) {
      this.agregarSiFalta(m); // dedup por id
    } else {
      const c = this.conversaciones.find(
        (x) => x.conversacionId === m.conversacionId,
      );
      if (c) {
        c.ultimoMensaje = m.contenido;
        c.noLeidos++;
      } else {
        this.cargarConversaciones();
      }
    }
  }

  private agregarSiFalta(m: MensajeChat): void {
    if (
      this.activa &&
      m.conversacionId === this.activa.conversacionId &&
      !this.mensajes.some((x) => x.id === m.id)
    ) {
      this.mensajes.push(m);
    }
  }
}
