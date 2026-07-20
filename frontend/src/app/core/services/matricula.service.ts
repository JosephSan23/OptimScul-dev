import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface MatriculaResumen {
  id: string;
  estudianteId: string;
  grupoId?: string;
  codigoMatricula: string;
  estudianteNombre: string;
  codigoEstudiante: string;
  numeroDocumento: string;
  grupoNombre?: string;
  gradoNombre?: string;
  tipo: string;
  estado: string;
  fechaMatricula: string;
}
export interface MatriculaRequest {
  estudianteId: string;
  anioLectivoId: string;
  tipo: string;
  grupoId?: string | null;
  fechaMatricula?: string | null;
  observaciones?: string;
}

@Injectable({ providedIn: 'root' })
export class MatriculaService {
  private readonly API = `${environment.apiUrl}/academico/matriculas`;
  constructor(private http: HttpClient) {}

  listarPorAnio(anioId: string): Observable<MatriculaResumen[]> {
    return this.http.get<MatriculaResumen[]>(`${this.API}/por-anio/${anioId}`);
  }
  obtener(id: string): Observable<any> {
    return this.http.get(`${this.API}/${id}`);
  }
  crear(d: MatriculaRequest): Observable<any> {
    return this.http.post(this.API, d);
  }
  asignarGrupo(id: string, grupoId: string): Observable<any> {
    return this.http.patch(`${this.API}/${id}/asignar-grupo/${grupoId}`, {});
  }
  retirar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/retirar`,
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
}
