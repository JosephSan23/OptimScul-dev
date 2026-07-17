import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface Area {
  id: string;
  codigo: string;
  nombre: string;
  descripcion?: string;
  activa: boolean;
  totalAsignaturas?: number;
}
export interface AreaRequest {
  codigo: string;
  nombre: string;
  descripcion?: string;
}

@Injectable({ providedIn: 'root' })
export class AreaService {
  private readonly API = `${environment.apiUrl}/academico/areas`;
  constructor(private http: HttpClient) {}
  listar(): Observable<Area[]> {
    return this.http.get<Area[]>(this.API);
  }
  obtener(id: string): Observable<Area> {
    return this.http.get<Area>(`${this.API}/${id}`);
  }
  crear(d: AreaRequest): Observable<Area> {
    return this.http.post<Area>(this.API, d);
  }
  editar(id: string, d: AreaRequest): Observable<Area> {
    return this.http.put<Area>(`${this.API}/${id}`, d);
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
