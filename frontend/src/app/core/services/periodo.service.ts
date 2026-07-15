import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface Periodo {
  id: string;
  anioLectivoId: string;
  numero: number;
  nombre: string;
  descripcion?: string;
  fechaInicio?: string;
  fechaFin?: string;
  peso?: number;
  estado: string;
}
export interface PeriodoRequest {
  numero: number | null;
  nombre: string;
  descripcion?: string;
  fechaInicio?: string;
  fechaFin?: string;
  peso?: number | null;
  estado?: string;
}

@Injectable({ providedIn: 'root' })
export class PeriodoService {
  private readonly API = `${environment.apiUrl}/config/periodos`;
  constructor(private http: HttpClient) {}
  listarPorAnio(anioId: string): Observable<Periodo[]> {
    return this.http.get<Periodo[]>(`${this.API}/por-anio/${anioId}`);
  }
  obtener(id: string): Observable<Periodo> {
    return this.http.get<Periodo>(`${this.API}/${id}`);
  }
  crear(anioId: string, d: PeriodoRequest): Observable<Periodo> {
    return this.http.post<Periodo>(`${this.API}/por-anio/${anioId}`, d);
  }
  editar(id: string, d: PeriodoRequest): Observable<Periodo> {
    return this.http.put<Periodo>(`${this.API}/${id}`, d);
  }
}
