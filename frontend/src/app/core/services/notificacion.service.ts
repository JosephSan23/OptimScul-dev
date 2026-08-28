import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Notificacion {
  id: string;
  notificacionId: string;
  tipo: string;
  titulo: string;
  mensaje: string;
  moduloRelacionado: string | null;
  entidadRelacionadaId: string | null;
  prioridad: number | null;
  estado: string;
  leidaEn: string | null;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class NotificacionService {
  private readonly API = `${environment.apiUrl}/notificaciones`;
  constructor(private http: HttpClient) {}

  bandeja(): Observable<Notificacion[]> {
    return this.http.get<Notificacion[]>(this.API);
  }
  contador(): Observable<{ noLeidas: number }> {
    return this.http.get<{ noLeidas: number }>(`${this.API}/contador`);
  }
  marcarLeida(id: string): Observable<void> {
    return this.http.patch<void>(`${this.API}/${id}/leer`, {});
  }
  marcarTodas(): Observable<void> {
    return this.http.patch<void>(`${this.API}/leer-todas`, {});
  }
  marcarChat(convId: string): Observable<void> {
    return this.http.patch<void>(`${this.API}/chat/${convId}/leer`, {});
  }
}
