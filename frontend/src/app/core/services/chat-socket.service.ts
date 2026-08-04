import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Subject, Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { AuthService } from './auth.service';
import { MensajeChat } from './chat.service';

@Injectable({ providedIn: 'root' })
export class ChatSocketService {
  private client?: any;                         // Client de @stomp/stompjs (cargado dinámicamente)
  private mensajes$ = new Subject<MensajeChat>();
  private notificaciones$ = new Subject<any>();
  get notificaciones(): Observable<any> { return this.notificaciones$.asObservable(); }

  constructor(
    private auth: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  get mensajes(): Observable<MensajeChat> {
    return this.mensajes$.asObservable();
  }

  async conectar(): Promise<void> {
    if (!isPlatformBrowser(this.platformId)) return;   // nunca en SSR
    if (this.client?.active) return;

    const token = this.auth.getToken();
    if (!token) return;

    const { Client } = await import('@stomp/stompjs');
    const SockJS = (await import('sockjs-client')).default;

    this.client = new Client({
      webSocketFactory: () => new SockJS(environment.wsUrl),
      connectHeaders: { Authorization: `Bearer ${token}` },
      reconnectDelay: 5000,
      onConnect: () => {
        this.client.subscribe('/user/queue/mensajes', (msg: any) => {
          this.mensajes$.next(JSON.parse(msg.body) as MensajeChat);
        });
        this.client.subscribe('/user/queue/notificaciones', (msg: any) => {
          this.notificaciones$.next(JSON.parse(msg.body));
        });
      },
    });
    this.client.activate();
  }

  desconectar(): void {
    this.client?.deactivate();
    this.client = undefined;
  }
}
