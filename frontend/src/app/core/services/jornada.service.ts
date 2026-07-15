import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface Jornada {
  id: string;
  codigo: string;
  nombre: string;
  descripcion?: string;
  horaInicio?: string;
  horaFin?: string;
  estado: string;
}
export interface JornadaRequest {
  codigo: string;
  nombre: string;
  descripcion?: string;
  horaInicio?: string;
  horaFin?: string;
}

@Injectable({ providedIn: 'root' })
export class JornadaService {
  private readonly API = `${environment.apiUrl}/config/jornadas`;
  constructor(private http: HttpClient) {}
  listar(): Observable<Jornada[]> {
    return this.http.get<Jornada[]>(this.API);
  }
  obtener(id: string): Observable<Jornada> {
    return this.http.get<Jornada>(`${this.API}/${id}`);
  }
  crear(d: JornadaRequest): Observable<Jornada> {
    return this.http.post<Jornada>(this.API, d);
  }
  editar(id: string, d: JornadaRequest): Observable<Jornada> {
    return this.http.put<Jornada>(`${this.API}/${id}`, d);
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
