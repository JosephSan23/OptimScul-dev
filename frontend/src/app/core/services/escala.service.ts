import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface Escala {
  id: string;
  nombre: string;
  abreviatura?: string;
  notaMinima: number;
  notaMaxima: number;
  aprueba: boolean;
  activa: boolean;
  orden: number;
}
export interface EscalaRequest {
  nombre: string;
  abreviatura?: string;
  notaMinima: number;
  notaMaxima: number;
  aprueba: boolean;
  orden: number;
}

@Injectable({ providedIn: 'root' })
export class EscalaService {
  private readonly API = `${environment.apiUrl}/academico/escalas`;
  constructor(private http: HttpClient) {}
  listar(): Observable<Escala[]> {
    return this.http.get<Escala[]>(this.API);
  }
  obtener(id: string): Observable<Escala> {
    return this.http.get<Escala>(`${this.API}/${id}`);
  }
  crear(d: EscalaRequest): Observable<Escala> {
    return this.http.post<Escala>(this.API, d);
  }
  editar(id: string, d: EscalaRequest): Observable<Escala> {
    return this.http.put<Escala>(`${this.API}/${id}`, d);
  }
  activar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/activar`,
      {},
    );
  }
  inactivar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/inactivar`,
      {},
    );
  }
}
