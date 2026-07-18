import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface Grupo {
  id: string;
  gradoId: string;
  anioLectivoId: string;
  sedeId?: string;
  jornadaId?: string;
  codigo: string;
  nombre: string;
  cupoMaximo?: number;
  estado: string;
  observaciones?: string;
}
export interface GrupoRequest {
  anioLectivoId: string;
  sedeId?: string | null;
  jornadaId?: string | null;
  codigo: string;
  nombre: string;
  cupoMaximo?: number | null;
  observaciones?: string;
}

@Injectable({ providedIn: 'root' })
export class GrupoService {
  private readonly API = `${environment.apiUrl}/academico/grupos`;
  constructor(private http: HttpClient) {}

  listarPorGrado(gradoId: string): Observable<Grupo[]> {
    return this.http.get<Grupo[]>(`${this.API}/por-grado/${gradoId}`);
  }
  obtener(id: string): Observable<Grupo> {
    return this.http.get<Grupo>(`${this.API}/${id}`);
  }
  crear(gradoId: string, d: GrupoRequest): Observable<Grupo> {
    return this.http.post<Grupo>(`${this.API}/por-grado/${gradoId}`, d);
  }
  editar(id: string, d: GrupoRequest): Observable<Grupo> {
    return this.http.put<Grupo>(`${this.API}/${id}`, d);
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
  cerrar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(`${this.API}/${id}/cerrar`, {});
  }

  listarPorAnio(anioId: string): Observable<Grupo[]> {
    return this.http.get<Grupo[]>(`${this.API}/por-anio/${anioId}`);
  }

}
