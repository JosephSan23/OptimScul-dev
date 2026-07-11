import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Sede {
  id: string;
  codigo: string;
  nombre: string;
  descripcion?: string;
  direccion?: string;
  telefono?: string;
  correo?: string;
  ciudad?: string;
  departamento?: string;
  pais?: string;
  principal: boolean;
  estado: string;
}
export interface SedeRequest {
  codigo: string;
  nombre: string;
  descripcion?: string;
  direccion?: string;
  telefono?: string;
  correo?: string;
  ciudad?: string;
  departamento?: string;
  pais?: string;
  principal: boolean;
}

@Injectable({ providedIn: 'root' })
export class SedeService {
  private readonly API = 'http://localhost:8080/api/config/sedes';
  constructor(private http: HttpClient) {}

  listar(): Observable<Sede[]> {
    return this.http.get<Sede[]>(this.API);
  }

  obtener(id: string): Observable<Sede> {
    return this.http.get<Sede>(`${this.API}/${id}`);
  }

  crear(d: SedeRequest): Observable<Sede> {
    return this.http.post<Sede>(this.API, d);
  }

  editar(id: string, d: SedeRequest): Observable<Sede> {
    return this.http.put<Sede>(`${this.API}/${id}`, d);
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
