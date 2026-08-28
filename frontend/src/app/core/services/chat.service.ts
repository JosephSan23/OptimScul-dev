import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Contacto {
  usuarioId: string;
  nombre: string;
  rol: string;
}
export interface ResumenConversacion {
  conversacionId: string;
  interlocutorId: string;
  interlocutorNombre: string;
  ultimoMensaje: string | null;
  fecha: string;
  noLeidos: number;
}
export interface MensajeChat {
  id: string;
  conversacionId: string;
  remitenteId: string;
  contenido: string;
  leido: boolean;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class ChatService {
  private readonly API = `${environment.apiUrl}/chat`;
  constructor(private http: HttpClient) {}

  contactos(): Observable<Contacto[]> {
    return this.http.get<Contacto[]>(`${this.API}/contactos`);
  }
  conversaciones(): Observable<ResumenConversacion[]> {
    return this.http.get<ResumenConversacion[]>(`${this.API}/conversaciones`);
  }
  abrirConversacion(destinatarioId: string): Observable<ResumenConversacion> {
    return this.http.post<ResumenConversacion>(`${this.API}/conversaciones`, {
      destinatarioId,
    });
  }
  historial(conversacionId: string): Observable<MensajeChat[]> {
    return this.http.get<MensajeChat[]>(
      `${this.API}/conversaciones/${conversacionId}/mensajes`,
    );
  }
  enviar(conversacionId: string, contenido: string): Observable<MensajeChat> {
    return this.http.post<MensajeChat>(
      `${this.API}/conversaciones/${conversacionId}/mensajes`,
      { contenido },
    );
  }
}
