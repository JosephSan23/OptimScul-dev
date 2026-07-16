import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';


export interface Grado {
  id: string;
  codigo: string;
  nombre: string;
  nivel: string;
  orden: number;
  estado: string;
}
export interface GradoRequest {
  codigo: string;
  nombre: string;
  nivel: string;
  orden: number;
}

@Injectable({ providedIn: 'root' })
export class GradoService {
  private readonly API = `${environment.apiUrl}/academico/grados`;
  constructor(private http: HttpClient) {}
  listar(): Observable<Grado[]> {
    return this.http.get<Grado[]>(this.API);
  }
  obtener(id: string): Observable<Grado> {
    return this.http.get<Grado>(`${this.API}/${id}`);
  }
  crear(d: GradoRequest): Observable<Grado> {
    return this.http.post<Grado>(this.API, d);
  }
  editar(id: string, d: GradoRequest): Observable<Grado> {
    return this.http.put<Grado>(`${this.API}/${id}`, d);
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
