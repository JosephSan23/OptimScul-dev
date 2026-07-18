import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface CargaResumen {
  id: string;
  profesorId: string;
  grupoId: string;
  asignaturaId: string;
  profesorNombre: string;
  codigoProfesor: string;
  asignaturaNombre: string;
  grupoNombre: string;
  grupoCodigo: string;
  gradoNombre: string;
  intensidadHorariaSemanal?: number;
  estado: string;
  observaciones?: string;
}
export interface CargaRequest {
  anioLectivoId: string;
  grupoId: string;
  asignaturaId: string;
  profesorId: string;
  intensidadHorariaSemanal?: number | null;
  fechaInicio?: string | null;
  fechaFin?: string | null;
  observaciones?: string;
}

@Injectable({ providedIn: 'root' })
export class CargaService {
  private readonly API = `${environment.apiUrl}/academico/cargas`;
  constructor(private http: HttpClient) {}
  listarPorAnio(anioId: string): Observable<CargaResumen[]> {
    return this.http.get<CargaResumen[]>(`${this.API}/por-anio/${anioId}`);
  }
  crear(d: CargaRequest): Observable<any> {
    return this.http.post(this.API, d);
  }
  editar(id: string, d: CargaRequest): Observable<any> {
    return this.http.put(`${this.API}/${id}`, d);
  }
  finalizar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/finalizar`,
      {},
    );
  }
  cancelar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/cancelar`,
      {},
    );
  }
  reactivar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/reactivar`,
      {},
    );
  }

  obtener(id: string): Observable<any> {
    return this.http.get(`${this.API}/${id}`);
  }
}
