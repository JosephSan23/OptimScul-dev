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

  iniciales(nombre?: string): string {
    if (!nombre) return '?';
    const partes = nombre.trim().split(/\s+/);
    const a = partes[0]?.[0] ?? '';
    const b = partes.length > 1 ? partes[partes.length - 1][0] : '';
    return (a + b).toUpperCase();
  }

  /** true si el mensaje i es del mismo emisor que el i-1 y el mismo día (agrupa la ráfaga). */
  esSeguido(i: number): boolean {
    if (i <= 0) return false;
    const prev = this.mensajes[i - 1];
    const act = this.mensajes[i];
    return (
      prev.remitenteId === act.remitenteId &&
      this.mismoDia(prev.createdAt, act.createdAt)
    );
  }

  /** true si hay que mostrar la hora: último de una ráfaga (cambia el emisor o es el último). */
  mostrarHora(i: number): boolean {
    const sig = this.mensajes[i + 1];
    if (!sig) return true;
    return (
      sig.remitenteId !== this.mensajes[i].remitenteId ||
      !this.mismoDia(this.mensajes[i].createdAt, sig.createdAt)
    );
  }

  /** true si el mensaje i abre un nuevo día respecto al anterior. */
  mostrarFecha(i: number): boolean {
    if (i === 0) return true;
    return !this.mismoDia(this.mensajes[i - 1].createdAt, this.mensajes[i].createdAt);
  }

  etiquetaFecha(m: MensajeChat): string {
    const f = new Date(m.createdAt);
    const hoy = new Date();
    const ayer = new Date();
    ayer.setDate(hoy.getDate() - 1);
    if (this.mismoDiaFecha(f, hoy)) return 'Hoy';
    if (this.mismoDiaFecha(f, ayer)) return 'Ayer';
    return f.toLocaleDateString('es-CO', {
      day: 'numeric',
      month: 'long',
      year: f.getFullYear() === hoy.getFullYear() ? undefined : 'numeric',
    });
  }

  private mismoDia(a: string | Date, b: string | Date): boolean {
    return this.mismoDiaFecha(new Date(a), new Date(b));
  }
  private mismoDiaFecha(a: Date, b: Date): boolean {
    return (
      a.getFullYear() === b.getFullYear() &&
      a.getMonth() === b.getMonth() &&
      a.getDate() === b.getDate()
    );
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
