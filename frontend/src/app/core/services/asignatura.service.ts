import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface Asignatura {
  id: string;
  areaId?: string;
  areaNombre?: string;
  codigo: string;
  nombre: string;
  descripcion?: string;
  intensidadHorariaSemanal?: number;
  requiereCalificacion: boolean;
  requiereRecuperacion: boolean;
  esComportamiento: boolean;
  activa: boolean;
}
export interface AsignaturaRequest {
  areaId?: string | null;
  codigo: string;
  nombre: string;
  descripcion?: string;
  intensidadHorariaSemanal?: number | null;
  requiereCalificacion?: boolean;
  requiereRecuperacion?: boolean;
  esComportamiento?: boolean;
}

@Injectable({ providedIn: 'root' })
export class AsignaturaService {
  private readonly API = `${environment.apiUrl}/academico/asignaturas`;
  constructor(private http: HttpClient) {}
  listar(): Observable<Asignatura[]> {
    return this.http.get<Asignatura[]>(this.API);
  }
  obtener(id: string): Observable<Asignatura> {
    return this.http.get<Asignatura>(`${this.API}/${id}`);
  }
  crear(d: AsignaturaRequest): Observable<Asignatura> {
    return this.http.post<Asignatura>(this.API, d);
  }
  editar(id: string, d: AsignaturaRequest): Observable<Asignatura> {
    return this.http.put<Asignatura>(`${this.API}/${id}`, d);
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
