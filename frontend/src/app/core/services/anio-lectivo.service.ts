import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';


export interface AnioLectivo {
  id: string;
  anio: number;
  nombre: string;
  descripcion?: string;
  fechaInicio?: string;
  fechaFin?: string;
  estado: string;
  esActual: boolean;
}
export interface AnioLectivoRequest {
  anio: number | null;
  nombre: string;
  descripcion?: string;
  fechaInicio?: string;
  fechaFin?: string;
  estado?: string;
}

@Injectable({ providedIn: 'root' })
export class AnioLectivoService {
  private readonly API = `${environment.apiUrl}/config/anios-lectivos`;
  constructor(private http: HttpClient) {}
  listar(): Observable<AnioLectivo[]> {
    return this.http.get<AnioLectivo[]>(this.API);
  }
  obtener(id: string): Observable<AnioLectivo> {
    return this.http.get<AnioLectivo>(`${this.API}/${id}`);
  }
  crear(d: AnioLectivoRequest): Observable<AnioLectivo> {
    return this.http.post<AnioLectivo>(this.API, d);
  }
  editar(id: string, d: AnioLectivoRequest): Observable<AnioLectivo> {
    return this.http.put<AnioLectivo>(`${this.API}/${id}`, d);
  }
  marcarActual(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/marcar-actual`,
      {},
    );
  }
}
